<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AloTra - Trà Sữa Ngon</title>
<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- FontAwesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<!-- Custom CSS -->
<style>
body {
	font-family: 'Segoe UI', sans-serif;
}
/* Hero Section */
.hero {
	height: 120vh;
	background: url('images/shopbantrasua.jpg') center/cover no-repeat;
	display: flex;
	align-items: center;
	justify-content: center;
	color: white;
	text-align: center;
}

.hero h1 {
	font-size: 4rem;
	font-weight: bold;
}

.hero .btn {
	padding: 12px 30px;
	font-size: 1.1rem;
}
/* Product Card */
.product-card {
	border-radius: 15px;
	overflow: hidden;
	box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
	transition: all .3s ease-in-out;
}

.product-card:hover {
	transform: translateY(-8px);
}

.product-card img {
	height: 250px;
	object-fit: cover;
}

.price {
	font-size: 1.2rem;
	font-weight: bold;
	color: #ff6600;
}
/* Footer */
footer {
	background: linear-gradient(45deg, #ff6600, #ff9966);
	color: white;
	padding: 50px 0 20px;
}

footer a {
	color: white;
	text-decoration: none;
}

footer a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>

	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg bg-light shadow-sm fixed-top">
		<div class="container">
			<a class="navbar-brand fw-bold text-danger" href="#">🍹 AloTra</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navMenu">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navMenu">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link" href="#">Trang
							Chủ</a></li>
					<li class="nav-item"><a class="nav-link" href="#menu">Thực
							Đơn</a></li>
					<li class="nav-item"><a class="nav-link" href="#about">Về
							Chúng Tôi</a></li>
					<li class="nav-item"><a class="nav-link" href="#contact">Liên
							Hệ</a></li>
					<li class="nav-item"><a class="btn btn-danger ms-3" href="#"><i
							class="fa fa-shopping-cart"></i> Giỏ Hàng</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Hero Section -->
	<section class="hero">
		<div>
			<h1>
				Trà Sữa <span style="color: #ff9966;">AloTra</span>
			</h1>
			<p class="lead">Thưởng thức hương vị trà sữa tươi ngon, nguyên
				liệu tự nhiên</p>
			<a href="#menu" class="btn btn-lg btn-warning me-2">Xem Thực Đơn</a>
			<a href="#about" class="btn btn-lg btn-outline-light">Khám Phá
				Thêm</a>
		</div>
	</section>

	<!-- Menu Section -->
	<section id="menu" class="py-5">
		<div class="container">
			<h2 class="text-center mb-4">Sản Phẩm Nổi Bật</h2>
			<div class="row g-4">
				<!-- Product Card -->
				<div class="col-md-4">
					<div class="card product-card">
						<img src="images/trasuatruyenthong.jpg" class="card-img-top"
							alt="Trà Sữa Truyền Thống">
						<div class="card-body text-center">
							<h5 class="card-title">Trà Sữa Truyền Thống</h5>
							<p class="price">35.000đ</p>
							<a href="#" class="btn btn-outline-danger">+ Thêm</a>
						</div>
					</div>
				</div>
				<!-- Product Card -->
				<div class="col-md-4">
					<div class="card product-card">
						<img src="images/machalatte.jpg" class="card-img-top"
							alt="Trà Sữa Matcha">
						<div class="card-body text-center">
							<h5 class="card-title">Trà Sữa Matcha</h5>
							<p class="price">42.000đ</p>
							<a href="#" class="btn btn-outline-danger">+ Thêm</a>
						</div>
					</div>
				</div>
				<!-- Product Card -->
				<div class="col-md-4">
					<div class="card product-card">
						<img src="images/trasuachocolate.jpg" class="card-img-top"
							alt="Trà Sữa Chocolate">
						<div class="card-body text-center">
							<h5 class="card-title">Trà Sữa Chocolate</h5>
							<p class="price">38.000đ</p>
							<a href="#" class="btn btn-outline-danger">+ Thêm</a>
						</div>
					</div>
				</div>
			</div>
			<div class="text-center mt-4">
				<a href="#" class="btn btn-lg btn-warning">Xem Tất Cả Sản Phẩm</a>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<footer class="text-center">
		<div class="container">
			<div class="mb-3">
				<h5>Tham Gia Cộng Đồng AloTra</h5>
				<form class="d-flex justify-content-center mt-3">
					<input type="email" class="form-control w-25 me-2"
						placeholder="Email của bạn">
					<button class="btn btn-dark">Đăng Ký</button>
				</form>
			</div>
			<p class="mt-4">© 2024 AloTra. Tất cả quyền được bảo lưu.</p>
			<p>Email: info@alotra.vn | Hotline: 1900 1234</p>
			<p>123 Đường ABC, Quận 1, TP.HCM</p>
		</div>
	</footer>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
