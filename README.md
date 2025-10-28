# AloTra Website - Website Bán Trà Sữa

## 📖 Giới thiệu

AloTra Website là một hệ thống thương mại điện tử chuyên về bán trà sữa trực tuyến, được xây dựng bằng Spring Boot 3.5.5 và Java 17. Website cung cấp đầy đủ các tính năng quản lý sản phẩm, đơn hàng, thanh toán và hệ thống phân quyền người dùng.

## 🛠️ Công nghệ sử dụng

- **Backend:** Spring Boot 3.5.5
- **Java Version:** Java 17
- **Database:** Microsoft SQL Server
- **Template Engine:** Thymeleaf
- **Security:** Spring Security
- **ORM:** Spring Data JPA (Hibernate)
- **Build Tool:** Maven
- **Other Libraries:**
  - Lombok
  - Cloudinary (Upload hình ảnh)
  - Spring Mail (Gửi email)
  - Bootstrap 5 (Frontend)

## 📋 Yêu cầu hệ thống

- Java JDK 17 trở lên
- Microsoft SQL Server (bất kỳ phiên bản tương thích)
- Maven 3.6+
- Port 8080 phải rảnh (hoặc có thể thay đổi trong `application.properties`)

## 🚀 Hướng dẫn cài đặt và chạy

### 1. Chuẩn bị Database

1. Cài đặt Microsoft SQL Server chạy 2 file  Scheam.sql và database_setup.sql
2. Tạo database tên `Website_BanTraSua`
3. Cấu hình kết nối trong file `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=Website_BanTraSua;encrypt=true;trustServerCertificate=true;sendStringParametersAsUnicode=true
spring.datasource.username=sa
spring.datasource.password=123456
```

**Lưu ý:** Thay đổi `username` và `password` phù hợp với cấu hình SQL Server của bạn.

### 2. Cài đặt Dependencies

Mở terminal tại thư mục gốc của project và chạy:

```bash
./mvnw clean install
```

Hoặc nếu đã cài Maven:

```bash
mvn clean install
```

### 3. Chạy ứng dụng

**Cách 1:** Sử dụng Maven Wrapper
```bash
./mvnw spring-boot:run
```

**Cách 2:** Sử dụng Maven
```bash
mvn spring-boot:run
```

**Cách 3:** Chạy file JAR/WAR
```bash
java -jar target/alotra-website-0.0.1-SNAPSHOT.war
```

### 4. Truy cập ứng dụng

Sau khi ứng dụng khởi động thành công, mở trình duyệt và truy cập:

```
http://localhost:8080/alotra
```

## 👥 Tài khoản đăng nhập

### 🔐 Admin (Quản trị viên)
- **Username:** `boss`
- **Password:** `123`
- **Quyền:** Quản lý toàn bộ hệ thống (sản phẩm, đơn hàng, người dùng, báo cáo, etc.)

### 👨‍💼 Nhân viên
- **Username:** `nva`
- **Password:** `123`
- **Quyền:** Xử lý đơn hàng, quản lý sản phẩm (giới hạn)

### 📝 Hướng dẫn đăng nhập

1. Truy cập: `http://localhost:8080/alotra`
2. Click vào nút **"Đăng nhập"** ở góc trên bên phải
3. Nhập thông tin tài khoản (username và password)
4. Click **"Đăng nhập"**
5. Hệ thống sẽ tự động chuyển hướng dựa trên quyền của người dùng:
   - **Admin** → Dashboard quản trị
   - **Nhân viên** → Trang quản lý đơn hàng
   - **Khách hàng** → Trang chủ

## 📁 Cấu trúc dự án

```
AloTraWebsite/
├── src/
│   ├── main/
│   │   ├── java/com/alotra/
│   │   │   ├── auth/              # Xác thực và phân quyền
│   │   │   ├── config/            # Cấu hình Spring
│   │   │   ├── controller/        # Controllers (xử lý request)
│   │   │   ├── dto/               # Data Transfer Objects
│   │   │   ├── entity/            # Entities (Models)
│   │   │   ├── repository/        # JPA Repositories
│   │   │   ├── security/          # Spring Security config
│   │   │   ├── service/           # Business Logic
│   │   │   ├── util/              # Utility classes
│   │   │   └── AloTraApplication.java  # Main Application
│   │   │
│   │   └── resources/
│   │       ├── application.properties  # Cấu hình ứng dụng
│   │       ├── static/            # Static resources
│   │       │   ├── css/           # CSS files
│   │       │   ├── js/            # JavaScript files
│   │       │   ├── images/        # Images
│   │       │   └── lib/           # Libraries (Bootstrap, etc.)
│   │       └── templates/         # Thymeleaf templates
│   │           ├── about/         # Trang giới thiệu
│   │           ├── account/       # Quản lý tài khoản
│   │           ├── admin/         # Trang quản trị
│   │           ├── auth/          # Đăng nhập/Đăng ký
│   │           ├── cart/          # Giỏ hàng
│   │           ├── category/      # Danh mục sản phẩm
│   │           ├── checkout/      # Thanh toán
│   │           ├── home/          # Trang chủ
│   │           ├── payment/       # Thanh toán
│   │           ├── products/      # Chi tiết sản phẩm
│   │           ├── promotions/    # Khuyến mãi
│   │           ├── search/        # Tìm kiếm
│   │           ├── shipper/       # Giao hàng
│   │           └── vendor/        # Nhà cung cấp
│   │
│   └── test/                      # Test cases
│
├── target/                        # Build output
├── pom.xml                        # Maven configuration
└── README.md                      # File này

```

## 🎯 Các tính năng chính

### 🛒 Khách hàng
- Xem danh sách sản phẩm trà sữa
- Tìm kiếm và lọc sản phẩm theo danh mục
- Thêm sản phẩm vào giỏ hàng
- Đặt hàng và thanh toán trực tuyến
- Theo dõi đơn hàng
- Quản lý tài khoản cá nhân

### 👨‍💼 Nhân viên
- Xử lý đơn hàng
- Cập nhật trạng thái đơn hàng
- Quản lý sản phẩm (thêm, sửa, xóa)
- Xem báo cáo đơn hàng

### 🔐 Admin
- Quản lý toàn bộ hệ thống
- Quản lý người dùng và phân quyền
- Quản lý danh mục sản phẩm
- Quản lý khuyến mãi
- Xem báo cáo thống kê
- Quản lý nhà cung cấp
- Cấu hình hệ thống

## ⚙️ Cấu hình

### Database
File: `src/main/resources/application.properties`
- Cấu hình kết nối SQL Server
- Timezone: Asia/Ho_Chi_Minh

### Email (SMTP)
Cấu hình gửi email cho các chức năng:
- Xác nhận đơn hàng
- Reset mật khẩu
- Thông báo trạng thái đơn hàng

### Cloudinary
Upload và quản lý hình ảnh sản phẩm

### Payment
Tích hợp cổng thanh toán qua webhook

## 🐛 Troubleshooting

### Lỗi kết nối Database
- Kiểm tra SQL Server đã chạy chưa
- Xác nhận thông tin đăng nhập trong `application.properties`
- Đảm bảo database `Website_BanTraSua` đã được tạo

### Port 8080 đã được sử dụng
Thay đổi port trong `application.properties`:
```properties
server.port=8081
```

### Lỗi Maven
Chạy lệnh clean và rebuild:
```bash
./mvnw clean install -U
```

## 📞 Liên hệ & Hỗ trợ

Nếu có vấn đề hoặc câu hỏi, vui lòng liên hệ:
- Email: nguyenphuoctai3717@gmail.com

## 📄 License

Copyright © 2025 AloTra Website. All rights reserved.

---

**Chúc bạn sử dụng thành công! 🎉**
