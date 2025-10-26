package com.alotra.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryService {

    @Value("${cloudinary.enabled:false}")
    private boolean enabled;

    @Value("${cloudinary.cloud-name:}")
    private String cloudName;

    @Value("${cloudinary.api-key:}")
    private String apiKey;

    @Value("${cloudinary.api-secret:}")
    private String apiSecret;

    @Value("${cloudinary.folder:alotra/products}")
    private String folder;

    @Value("${cloudinary.url:}")
    private String cloudinaryUrl;

    // Avoid hard dependency: keep as Object and use reflection
    private Object cloudinary;

    private synchronized void initIfNeeded() {
        if (cloudinary == null && isConfigured()) {
            try {
                Class<?> cloudClass = Class.forName("com.cloudinary.Cloudinary");
                // Prefer URL constructor when provided to avoid mismatch between fields
                String url = effectiveUrl();
                if (notBlank(url)) {
                    Constructor<?> urlCtor = cloudClass.getConstructor(String.class);
                    cloudinary = urlCtor.newInstance(url);
                } else {
                    Constructor<?> mapCtor = cloudClass.getConstructor(Map.class);
                    Map<String, Object> config = new HashMap<>();
                    config.put("cloud_name", cloudName);
                    config.put("api_key", apiKey);
                    config.put("api_secret", apiSecret);
                    config.put("secure", true);
                    cloudinary = mapCtor.newInstance(config);
                }
            } catch (Exception e) {
                log.error("Cloudinary init failed: {}", e.getMessage());
            }
        }
    }

    public boolean isConfigured() {
        if (!enabled) return false;
        // If URL present and looks valid, accept it
        String url = effectiveUrl();
        if (isValidUrl(url)) return true;

        // Otherwise require individual fields; try to hydrate from env URL if missing/placeholder
        if (!validValue(cloudName) || !validValue(apiKey) || !validValue(apiSecret)) {
            hydrateFromEnvIfBlank();
        }
        boolean ok = validValue(cloudName) && validValue(apiKey) && validValue(apiSecret);
        if (!ok) {
            log.warn("Cloudinary is enabled but not fully configured. cloudName set? {} apiKey set? {} apiSecret set? {} (no URL detected)",
                validValue(cloudName), validValue(apiKey), validValue(apiSecret));
        }
        return ok;
    }

    private boolean notBlank(String s) { return s != null && !s.trim().isEmpty(); }

    private boolean isPlaceholder(String s) {
        if (s == null) return false;
        String t = s.trim();
        return t.contains("<") || t.contains(">") || t.equalsIgnoreCase("YOUR_API_KEY") || t.contains("YOUR_API_KEY");
    }

    private boolean validValue(String s) {
        return notBlank(s) && !isPlaceholder(s);
    }

    private String effectiveUrl() {
        String prop = this.cloudinaryUrl;
        if (!notBlank(prop)) prop = System.getenv("CLOUDINARY_URL");
        return prop;
    }

    private boolean isValidUrl(String url) {
        if (!notBlank(url)) return false;
        if (!url.startsWith("cloudinary://")) return false;
        // Basic placeholder guard
        return !isPlaceholder(url);
    }

    /**
     * Support CLOUDINARY_URL env var: cloudinary://<api_key>:<api_secret>@<cloud_name>
     */
    private void hydrateFromEnvIfBlank() {
        try {
            // Prefer property if provided
            String url = effectiveUrl();
            if (!notBlank(url)) return;
            // Example: cloudinary://1234567890:abcDEFghiJKLmnopQRSTuvwxYZ@dxqpivcnu
            if (!url.startsWith("cloudinary://")) return;
            String rest = url.substring("cloudinary://".length());
            int at = rest.indexOf('@');
            if (at <= 0) return;
            String creds = rest.substring(0, at);
            String cloud = rest.substring(at + 1);
            int colon = creds.indexOf(':');
            if (colon <= 0) return;
            String key = creds.substring(0, colon);
            String secret = creds.substring(colon + 1);
            if (!validValue(this.cloudName)) this.cloudName = cloud;
            if (!validValue(this.apiKey)) this.apiKey = key;
            if (!validValue(this.apiSecret)) this.apiSecret = secret;
        } catch (Exception ignored) {
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        if (!isConfigured()) {
            throw new IllegalStateException("Cloudinary is not configured or disabled.");
        }
        initIfNeeded();
        if (cloudinary == null) {
            throw new IllegalStateException("Cloudinary SDK not found on classpath.");
        }
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("folder", folder);
            options.put("resource_type", "image");
            options.put("use_filename", true);
            options.put("unique_filename", true);
            options.put("overwrite", true);

            Method uploaderMethod = cloudinary.getClass().getMethod("uploader");
            Object uploader = uploaderMethod.invoke(cloudinary);
            Method uploadMethod = uploader.getClass().getMethod("upload", Object.class, Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = (Map<String, Object>) uploadMethod.invoke(uploader, file.getBytes(), options);
            String secureUrl = (String) uploadResult.getOrDefault("secure_url", uploadResult.get("url"));
            log.info("Uploaded image to Cloudinary: {}", secureUrl);
            return secureUrl;
        } catch (Exception e) {
            log.error("Cloudinary upload failed", e);
            String msg = e.getMessage();
            if (msg == null && e.getCause() != null) msg = e.getCause().getMessage();
            if (msg == null) msg = e.getClass().getSimpleName();
            throw new IOException("Cloudinary upload failed: " + msg, e);
        }
    }

    public void deleteByUrl(String imageUrl) {
        try {
            if (!isConfigured()) return;
            initIfNeeded();
            if (cloudinary == null) return;
            String publicId = extractPublicId(imageUrl);
            if (publicId == null) return;
            Method uploaderMethod = cloudinary.getClass().getMethod("uploader");
            Object uploader = uploaderMethod.invoke(cloudinary);
            Method destroyMethod = uploader.getClass().getMethod("destroy", String.class, Map.class);
            Map<String, Object> empty = new HashMap<>();
            Object result = destroyMethod.invoke(uploader, publicId, empty);
            log.info("Cloudinary destroy result for {}: {}", publicId, String.valueOf(result));
        } catch (Exception e) {
            log.warn("Failed to delete image from Cloudinary: {}", imageUrl, e);
        }
    }

    /**
     * Convert a Cloudinary URL like:
     * https://res.cloudinary.com/<cloud>/image/upload/v12345/folder/filename.png
     * to public_id: folder/filename
     */
    private String extractPublicId(String url) {
        try {
            if (url == null || !url.contains("res.cloudinary.com")) return null;
            // Keep path after '/upload/'
            int idx = url.indexOf("/upload/");
            if (idx == -1) return null;
            String tail = url.substring(idx + "/upload/".length());
            // Remove version segment if present (e.g., v1234567890/)
            if (tail.startsWith("v")) {
                int slash = tail.indexOf('/');
                if (slash != -1) tail = tail.substring(slash + 1);
            }
            // Remove extension
            int dot = tail.lastIndexOf('.');
            if (dot != -1) tail = tail.substring(0, dot);
            return tail;
        } catch (Exception e) {
            return null;
        }
    }

    // Alias for compatibility
    public String uploadFile(MultipartFile file) throws IOException {
        return uploadImage(file);
    }
}
