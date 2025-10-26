USE [master]
GO
/****** Object:  Database [Website_BanTraSua]    Script Date: 10/17/2025 11:31:09 AM ******/
CREATE DATABASE [Website_BanTraSua]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Website_BanTraSua', FILENAME = N'E:\LT Window\MSSQL16.MSSQLSERVER\MSSQL\DATA\Website_BanTraSua.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Website_BanTraSua_log', FILENAME = N'E:\LT Window\MSSQL16.MSSQLSERVER\MSSQL\DATA\Website_BanTraSua_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [Website_BanTraSua] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Website_BanTraSua].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Website_BanTraSua] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET ARITHABORT OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Website_BanTraSua] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Website_BanTraSua] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Website_BanTraSua] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Website_BanTraSua] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET RECOVERY FULL 
GO
ALTER DATABASE [Website_BanTraSua] SET  MULTI_USER 
GO
ALTER DATABASE [Website_BanTraSua] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Website_BanTraSua] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Website_BanTraSua] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Website_BanTraSua] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Website_BanTraSua] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Website_BanTraSua] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Website_BanTraSua', N'ON'
GO
ALTER DATABASE [Website_BanTraSua] SET QUERY_STORE = ON
GO
ALTER DATABASE [Website_BanTraSua] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Website_BanTraSua]
GO
/****** Object:  Table [dbo].[CTDonHang]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTDonHang](
	[MaCT] [int] IDENTITY(1,1) NOT NULL,
	[MaDH] [int] NOT NULL,
	[MaBT] [int] NOT NULL,
	[SoLuong] [int] NOT NULL,
	[DonGia] [decimal](10, 2) NOT NULL,
	[GiamGiaDong] [decimal](10, 2) NOT NULL,
	[ThanhTien] [decimal](10, 2) NOT NULL,
	[GhiChu] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DonHang]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DonHang](
	[MaDH] [int] IDENTITY(1,1) NOT NULL,
	[MaKH] [int] NOT NULL,
	[MaNV] [int] NULL,
	[MaKM] [int] NULL,
	[NgayLap] [datetime2](7) NOT NULL,
	[TrangThaiDonHang] [nvarchar](30) NOT NULL,
	[PaymentStatus] [nvarchar](30) NOT NULL,
	[PaymentMethod] [nvarchar](50) NULL,
	[PaidAt] [datetime2](7) NULL,
	[TongHang] [decimal](12, 2) NOT NULL,
	[GiamGiaDon] [decimal](12, 2) NOT NULL,
	[PhiVanChuyen] [decimal](12, 2) NOT NULL,
	[TongThanhToan] [decimal](12, 2) NOT NULL,
	[GhiChu] [nvarchar](500) NULL,
	[PhuongThucNhanHang] [nvarchar](50) NULL,
	[DiaChiNhanHang] [nvarchar](max) NULL,
	[TenNguoiNhan] [nvarchar](50) NULL,
	[SDTNguoiNhan] [nchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaDH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  View [dbo].[v_DonHang_TinhTong]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[v_DonHang_TinhTong]
AS
SELECT
    dh.MaDH,
    SUM(ct.ThanhTien) AS TongHangDong,
    dh.GiamGiaDon,
    dh.PhiVanChuyen,
    (SUM(ct.ThanhTien) - dh.GiamGiaDon + dh.PhiVanChuyen) AS TongPhaiTra
FROM dbo.DonHang dh
JOIN dbo.CTDonHang ct ON ct.MaDH = dh.MaDH
GROUP BY dh.MaDH, dh.GiamGiaDon, dh.PhiVanChuyen;
GO
/****** Object:  Table [dbo].[BienTheSanPham]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BienTheSanPham](
	[MaBT] [int] IDENTITY(1,1) NOT NULL,
	[MaSP] [int] NOT NULL,
	[MaSize] [int] NOT NULL,
	[GiaBan] [decimal](10, 2) NOT NULL,
	[TrangThai] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaBT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CTDonHang_Topping]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTDonHang_Topping](
	[MaCT] [int] NOT NULL,
	[MaTopping] [int] NOT NULL,
	[SoLuong] [int] NOT NULL,
	[DonGia] [decimal](10, 2) NOT NULL,
	[ThanhTien] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCT] ASC,
	[MaTopping] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DanhGia]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhGia](
	[MaDG] [int] IDENTITY(1,1) NOT NULL,
	[MaKH] [int] NOT NULL,
	[MaCT] [int] NOT NULL,
	[SoSao] [int] NOT NULL,
	[BinhLuan] [nvarchar](max) NULL,
	[NgayDG] [datetime2](7) NOT NULL,
	[TraLoiAdmin] [nvarchar](max) NULL,
	[TraLoiLuc] [datetime2](7) NULL,
	[TraLoiBoi] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaDG] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DanhMucSanPham]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhMucSanPham](
	[MaDM] [int] IDENTITY(1,1) NOT NULL,
	[TenDM] [nvarchar](255) NOT NULL,
	[MoTa] [nvarchar](max) NULL,
	[DeletedAt] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaDM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GioHang]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GioHang](
	[MaGH] [int] IDENTITY(1,1) NOT NULL,
	[MaKH] [int] NOT NULL,
	[TrangThai] [nvarchar](20) NOT NULL,
	[CreatedAt] [datetime2](7) NOT NULL,
	[UpdatedAt] [datetime2](7) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaGH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GioHangCT]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GioHangCT](
	[MaCTGH] [int] IDENTITY(1,1) NOT NULL,
	[MaGH] [int] NOT NULL,
	[MaBT] [int] NOT NULL,
	[SoLuong] [int] NOT NULL,
	[DonGia] [decimal](10, 2) NOT NULL,
	[ThanhTien] [decimal](10, 2) NOT NULL,
	[GhiChu] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCTGH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GioHangCT_Topping]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GioHangCT_Topping](
	[MaCTGH] [int] NOT NULL,
	[MaTopping] [int] NOT NULL,
	[SoLuong] [int] NOT NULL,
	[DonGia] [decimal](10, 2) NOT NULL,
	[ThanhTien] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCTGH] ASC,
	[MaTopping] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhachHang]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[MaKH] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](50) NOT NULL,
	[MatKhauHash] [nvarchar](255) NOT NULL,
	[Email] [nvarchar](255) NOT NULL,
	[TenKH] [nvarchar](255) NOT NULL,
	[SoDienThoai] [nvarchar](20) NULL,
	[TrangThai] [tinyint] NOT NULL,
	[AnhDaiDien] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhuyenMaiSanPham]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhuyenMaiSanPham](
	[MaKM] [int] NOT NULL,
	[MaSP] [int] NOT NULL,
	[PhanTramGiam] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaKM] ASC,
	[MaSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[MaNV] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](50) NOT NULL,
	[MatKhauHash] [nvarchar](255) NOT NULL,
	[Email] [nvarchar](255) NOT NULL,
	[TenNV] [nvarchar](255) NOT NULL,
	[VaiTro] [tinyint] NOT NULL,
	[SoDienThoai] [nvarchar](20) NULL,
	[TrangThai] [tinyint] NOT NULL,
	[AnhDaiDien] [nvarchar](500) NULL,
	[DeletedAt] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OtpCodes]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OtpCodes](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[KhachHangId] [int] NOT NULL,
	[Code] [varchar](10) NOT NULL,
	[Type] [varchar](30) NOT NULL,
	[ExpiresAt] [datetime] NOT NULL,
	[UsedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductMedia]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductMedia](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[IsPrimary] [bit] NULL,
	[Url] [varchar](255) NOT NULL,
	[ProductId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SanPham]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SanPham](
	[MaSP] [int] IDENTITY(1,1) NOT NULL,
	[MaDM] [int] NOT NULL,
	[TenSP] [nvarchar](255) NOT NULL,
	[MoTa] [nvarchar](255) NULL,
	[TrangThai] [tinyint] NOT NULL,
	[UrlAnh] [varchar](255) NULL,
	[DeletedAt] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SizeSanPham]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SizeSanPham](
	[MaSize] [int] IDENTITY(1,1) NOT NULL,
	[TenSize] [nvarchar](10) NOT NULL,
	[TrangThai] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaSize] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SuKienKhuyenMai]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SuKienKhuyenMai](
	[MaKM] [int] IDENTITY(1,1) NOT NULL,
	[TenSuKien] [nvarchar](255) NOT NULL,
	[MoTa] [nvarchar](255) NULL,
	[NgayBD] [date] NOT NULL,
	[NgayKT] [date] NOT NULL,
	[TrangThai] [tinyint] NOT NULL,
	[UrlAnh] [nvarchar](max) NULL,
	[LuotXem] [int] NULL,
	[DeletedAt] [datetime2](7) NULL,
 CONSTRAINT [PK__SuKienKh__2725CF15FA1FF15C] PRIMARY KEY CLUSTERED 
(
	[MaKM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Topping]    Script Date: 10/17/2025 11:31:10 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Topping](
	[MaTopping] [int] IDENTITY(1,1) NOT NULL,
	[TenTopping] [nvarchar](255) NOT NULL,
	[GiaThem] [decimal](10, 2) NOT NULL,
	[TrangThai] [tinyint] NOT NULL,
	[UrlAnh] [varchar](255) NULL,
	[DeletedAt] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaTopping] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[BienTheSanPham] ON 

INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (1, 1, 1, CAST(20000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (2, 1, 2, CAST(25000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (3, 1, 3, CAST(30000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (4, 2, 1, CAST(22000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (5, 2, 2, CAST(27000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (6, 2, 3, CAST(32000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (7, 3, 1, CAST(25000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (8, 3, 2, CAST(30000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (9, 3, 3, CAST(35000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (10, 4, 1, CAST(18000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (11, 4, 2, CAST(22000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (12, 4, 3, CAST(27000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (14, 8, 1, CAST(18000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (15, 8, 2, CAST(20000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (16, 8, 3, CAST(22000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (17, 7, 3, CAST(24000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (18, 9, 1, CAST(1000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (19, 9, 2, CAST(2000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (20, 5, 2, CAST(45000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (21, 10, 1, CAST(250000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (22, 10, 2, CAST(27000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (23, 10, 3, CAST(29000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (24, 11, 1, CAST(19000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (25, 11, 2, CAST(23000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (26, 11, 3, CAST(27000.00 AS Decimal(10, 2)), 1)
INSERT [dbo].[BienTheSanPham] ([MaBT], [MaSP], [MaSize], [GiaBan], [TrangThai]) VALUES (27, 14, 1, CAST(12000.00 AS Decimal(10, 2)), 1)
SET IDENTITY_INSERT [dbo].[BienTheSanPham] OFF
GO
SET IDENTITY_INSERT [dbo].[CTDonHang] ON 

INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (1, 1, 2, 2, CAST(25000.00 AS Decimal(10, 2)), CAST(2000.00 AS Decimal(10, 2)), CAST(48000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (2, 1, 5, 1, CAST(27000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(27000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (3, 2, 14, 2, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(36000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (4, 3, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(39000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (5, 3, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(45000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (6, 3, 14, 3, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(66000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (7, 4, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(39000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (8, 5, 14, 3, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(66000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (9, 6, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(39000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (10, 7, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(39000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (11, 8, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(45000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (12, 9, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(30000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (13, 10, 11, 1, CAST(22000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(26000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (14, 11, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (15, 12, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (16, 13, 9, 1, CAST(35000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(40000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (17, 14, 7, 1, CAST(25000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(25000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (18, 15, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (19, 16, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (20, 16, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (21, 17, 10, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (22, 18, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(23000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (23, 19, 10, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(28000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (24, 19, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (25, 20, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (26, 21, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (27, 22, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (28, 23, 18, 1, CAST(1000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(1000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (29, 24, 18, 1, CAST(1000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(1000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (30, 25, 18, 1, CAST(1000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(1000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (31, 26, 18, 1, CAST(1000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(1000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (32, 27, 19, 1, CAST(2000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(2000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (33, 28, 18, 2, CAST(1000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(2000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (34, 29, 18, 2, CAST(1000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(2000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (35, 30, 19, 1, CAST(2000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(2000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (36, 31, 19, 1, CAST(2000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(2000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (37, 32, 19, 1, CAST(2000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(2000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (38, 33, 19, 1, CAST(2000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(2000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (39, 34, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (40, 35, 17, 1, CAST(19200.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(19200.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (41, 36, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (42, 37, 10, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (43, 38, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (44, 39, 14, 1, CAST(18000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (45, 40, 12, 1, CAST(27000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(48000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (46, 41, 17, 1, CAST(19200.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(19200.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (47, 42, 17, 1, CAST(19200.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(19200.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (48, 43, 15, 3, CAST(17000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(101000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (49, 44, 17, 1, CAST(19200.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(19200.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (50, 44, 11, 1, CAST(20900.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(20900.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (51, 45, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(74000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (52, 45, 14, 1, CAST(15300.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(15300.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (53, 45, 14, 2, CAST(15300.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(50600.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (54, 46, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (55, 46, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (56, 47, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (57, 48, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (58, 49, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(39000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (59, 50, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(39000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (60, 50, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(32100.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (61, 51, 14, 1, CAST(15300.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(20300.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (62, 52, 23, 1, CAST(29000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(44000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Nhiều')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (63, 53, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (64, 54, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (65, 55, 23, 1, CAST(29000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(29000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (66, 55, 14, 1, CAST(15300.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(15300.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (67, 55, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(35000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (68, 55, 7, 1, CAST(21250.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(32250.00 AS Decimal(10, 2)), N'Đường: Nhiều; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (69, 55, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (70, 56, 14, 1, CAST(15300.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(15300.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (71, 57, 22, 1, CAST(27000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(27000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (72, 57, 14, 1, CAST(15300.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(15300.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (73, 57, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (74, 58, 20, 1, CAST(45000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(45000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (75, 59, 7, 1, CAST(21250.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(21250.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (76, 60, 14, 1, CAST(15300.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(26300.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Nhiều')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (77, 61, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (78, 62, 20, 1, CAST(45000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(45000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (79, 63, 7, 1, CAST(21250.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(21250.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (80, 64, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (81, 65, 7, 1, CAST(21250.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(21250.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (82, 66, 22, 1, CAST(27000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(27000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (83, 66, 16, 1, CAST(18700.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(18700.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (84, 66, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (85, 67, 9, 2, CAST(29750.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(89500.00 AS Decimal(10, 2)), N'Đường: Nhiều; Đá: Nhiều')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (86, 67, 14, 1, CAST(15300.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(15300.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (87, 67, 22, 1, CAST(27000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(27000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (88, 68, 12, 1, CAST(25650.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(40650.00 AS Decimal(10, 2)), N'Đường: Nhiều; Đá: Nhiều')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (89, 69, 11, 1, CAST(20900.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(20900.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (90, 70, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (91, 71, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (92, 72, 7, 1, CAST(21250.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(21250.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (93, 73, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (94, 74, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (95, 75, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (96, 76, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (97, 77, 17, 1, CAST(24000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(24000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (98, 78, 14, 1, CAST(15300.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(15300.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (99, 79, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
GO
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (100, 80, 7, 1, CAST(21250.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(21250.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (101, 81, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (102, 82, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (103, 83, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (104, 84, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (105, 85, 10, 1, CAST(17100.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(17100.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (106, 86, 22, 1, CAST(27000.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(27000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
INSERT [dbo].[CTDonHang] ([MaCT], [MaDH], [MaBT], [SoLuong], [DonGia], [GiamGiaDong], [ThanhTien], [GhiChu]) VALUES (107, 87, 7, 1, CAST(21250.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(21250.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
SET IDENTITY_INSERT [dbo].[CTDonHang] OFF
GO
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (1, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (1, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (2, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (4, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (4, 2, 2, CAST(6000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (4, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (5, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (5, 2, 2, CAST(6000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (5, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (6, 3, 3, CAST(4000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (7, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (7, 2, 2, CAST(6000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (7, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (8, 3, 3, CAST(4000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (9, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (9, 2, 2, CAST(6000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (9, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (10, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (10, 2, 2, CAST(6000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (10, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (11, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (11, 2, 2, CAST(6000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (11, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (12, 3, 3, CAST(4000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (13, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (16, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (22, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (23, 1, 2, CAST(5000.00 AS Decimal(10, 2)), CAST(10000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (45, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (45, 2, 2, CAST(6000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (45, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (48, 1, 4, CAST(5000.00 AS Decimal(10, 2)), CAST(20000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (48, 2, 5, CAST(6000.00 AS Decimal(10, 2)), CAST(30000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (51, 1, 4, CAST(5000.00 AS Decimal(10, 2)), CAST(20000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (51, 2, 3, CAST(6000.00 AS Decimal(10, 2)), CAST(18000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (51, 3, 3, CAST(4000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (53, 2, 2, CAST(6000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (53, 3, 2, CAST(4000.00 AS Decimal(10, 2)), CAST(8000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (58, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (58, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (58, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (59, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (59, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (59, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (60, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (60, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (60, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (61, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (62, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (62, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (62, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (67, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (67, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (68, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (68, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (76, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (76, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (85, 1, 2, CAST(5000.00 AS Decimal(10, 2)), CAST(10000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (85, 2, 2, CAST(6000.00 AS Decimal(10, 2)), CAST(12000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (85, 3, 2, CAST(4000.00 AS Decimal(10, 2)), CAST(8000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (88, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (88, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[CTDonHang_Topping] ([MaCT], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (88, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
GO
SET IDENTITY_INSERT [dbo].[DanhGia] ON 

INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (1, 1, 1, 5, N'Ngon, đúng ý!', CAST(N'2025-10-03T12:52:38.3668110' AS DateTime2), NULL, NULL, NULL)
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (2, 3, 23, 5, N'thức uống tuyệt diệu', CAST(N'2025-10-07T19:21:04.3585287' AS DateTime2), N'Cảm ơn bạn, mong bạn tiếp tục ủng hộ quán.', CAST(N'2025-10-13T16:03:14.2186254' AS DateTime2), N'boss')
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (3, 3, 24, 5, N'', CAST(N'2025-10-07T19:21:07.8657794' AS DateTime2), NULL, NULL, NULL)
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (4, 3, 48, 5, N'Dịu!!!', CAST(N'2025-10-07T20:02:16.2076981' AS DateTime2), NULL, NULL, NULL)
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (5, 3, 57, 4, N'Ngonnn', CAST(N'2025-10-09T13:57:45.6120797' AS DateTime2), NULL, NULL, NULL)
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (6, 3, 65, 5, N'', CAST(N'2025-10-12T19:01:42.5255015' AS DateTime2), NULL, NULL, NULL)
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (7, 3, 66, 5, N'', CAST(N'2025-10-12T19:01:47.8222021' AS DateTime2), NULL, NULL, NULL)
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (8, 3, 67, 5, N'', CAST(N'2025-10-12T19:01:49.2534779' AS DateTime2), NULL, NULL, NULL)
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (9, 3, 68, 5, N'', CAST(N'2025-10-12T19:01:50.3736789' AS DateTime2), NULL, NULL, NULL)
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (10, 3, 69, 5, N'', CAST(N'2025-10-12T19:01:51.7974871' AS DateTime2), NULL, NULL, NULL)
INSERT [dbo].[DanhGia] ([MaDG], [MaKH], [MaCT], [SoSao], [BinhLuan], [NgayDG], [TraLoiAdmin], [TraLoiLuc], [TraLoiBoi]) VALUES (11, 3, 64, 5, N'', CAST(N'2025-10-15T18:35:38.9012344' AS DateTime2), NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[DanhGia] OFF
GO
SET IDENTITY_INSERT [dbo].[DanhMucSanPham] ON 

INSERT [dbo].[DanhMucSanPham] ([MaDM], [TenDM], [MoTa], [DeletedAt]) VALUES (1, N'Trà sữa', N'Các loại trà sữa đặc biệt', NULL)
INSERT [dbo].[DanhMucSanPham] ([MaDM], [TenDM], [MoTa], [DeletedAt]) VALUES (2, N'Trà trái cây', N'Trà kết hợp trái cây tươi', NULL)
INSERT [dbo].[DanhMucSanPham] ([MaDM], [TenDM], [MoTa], [DeletedAt]) VALUES (3, N'Nước ép', N'Nước ép hoa quả nguyên chất', NULL)
INSERT [dbo].[DanhMucSanPham] ([MaDM], [TenDM], [MoTa], [DeletedAt]) VALUES (4, N'Đá xay', N'Thức uống mát lạnh tê tái
', NULL)
INSERT [dbo].[DanhMucSanPham] ([MaDM], [TenDM], [MoTa], [DeletedAt]) VALUES (5, N'Cà phê', N'Cà phê', NULL)
INSERT [dbo].[DanhMucSanPham] ([MaDM], [TenDM], [MoTa], [DeletedAt]) VALUES (6, N'Trà nguyên vị', N'Trà nguyên vị, đậm đà', CAST(N'2025-10-12T10:07:55.4114420' AS DateTime2))
INSERT [dbo].[DanhMucSanPham] ([MaDM], [TenDM], [MoTa], [DeletedAt]) VALUES (7, N'Cocktail', N'Cocktail kết hợp trà', CAST(N'2025-10-12T09:04:42.3538530' AS DateTime2))
SET IDENTITY_INSERT [dbo].[DanhMucSanPham] OFF
GO
SET IDENTITY_INSERT [dbo].[DonHang] ON 

INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (1, 1, 1, 1, CAST(N'2025-10-03T12:52:38.3631379' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(54000.00 AS Decimal(12, 2)), CAST(5000.00 AS Decimal(12, 2)), CAST(10000.00 AS Decimal(12, 2)), CAST(59000.00 AS Decimal(12, 2)), N'Giao trước 17h', NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (2, 3, 3, NULL, CAST(N'2025-10-05T13:54:38.9540155' AS DateTime2), N'DaGiao', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(36000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(36000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (3, 7, 3, NULL, CAST(N'2025-10-05T15:02:03.0446986' AS DateTime2), N'DaGiao', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(150000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(150000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (4, 7, NULL, NULL, CAST(N'2025-10-05T15:07:57.9552419' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(39000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(39000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (5, 7, NULL, NULL, CAST(N'2025-10-05T15:13:53.1776726' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(66000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(66000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (6, 7, NULL, NULL, CAST(N'2025-10-05T15:19:48.6726877' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(39000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(39000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (7, 7, NULL, NULL, CAST(N'2025-10-05T15:22:14.9357738' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(39000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(39000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (8, 7, NULL, NULL, CAST(N'2025-10-05T15:23:11.4081311' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(45000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(45000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (9, 7, NULL, NULL, CAST(N'2025-10-05T15:23:18.4160583' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(30000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(30000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (10, 7, NULL, NULL, CAST(N'2025-10-05T15:33:59.1261204' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'Tiền mặt', NULL, CAST(26000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(26000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (11, 3, NULL, NULL, CAST(N'2025-10-05T18:49:34.0526462' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'Momo', NULL, CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (12, 3, NULL, NULL, CAST(N'2025-10-05T18:53:56.9512288' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'TienMat', NULL, CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (13, 3, NULL, NULL, CAST(N'2025-10-05T18:56:41.6301235' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'TienMat', NULL, CAST(40000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(40000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (14, 3, NULL, NULL, CAST(N'2025-10-05T19:03:37.1850597' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-06T10:32:29.8131614' AS DateTime2), CAST(25000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(25000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (15, 3, NULL, NULL, CAST(N'2025-10-05T19:05:49.9369755' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'TienMat', NULL, CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (16, 3, NULL, NULL, CAST(N'2025-10-05T19:06:16.4579248' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'TienMat', NULL, CAST(42000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(42000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (17, 3, NULL, NULL, CAST(N'2025-10-05T19:06:40.3597967' AS DateTime2), N'DaGiao', N'ChuaThanhToan', N'TienMat', NULL, CAST(18000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(18000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (18, 3, NULL, NULL, CAST(N'2025-10-05T20:14:54.4927102' AS DateTime2), N'DangGiao', N'ChuaThanhToan', N'Momo', NULL, CAST(23000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(23000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (19, 3, 3, NULL, CAST(N'2025-10-06T09:17:05.6339172' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-06T10:31:39.4930276' AS DateTime2), CAST(46000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(46000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (20, 3, NULL, NULL, CAST(N'2025-10-06T09:32:37.8461040' AS DateTime2), N'ChoXuLy', N'DaThanhToan', N'ChuyenKhoan', CAST(N'2025-10-06T09:33:38.0429203' AS DateTime2), CAST(18000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(18000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (21, 3, NULL, NULL, CAST(N'2025-10-06T09:48:07.5586498' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (22, 3, NULL, NULL, CAST(N'2025-10-06T10:03:13.4374201' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(18000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(18000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (23, 3, NULL, NULL, CAST(N'2025-10-06T10:12:11.6164822' AS DateTime2), N'ChoXuLy', N'DaThanhToan', N'TienMat', CAST(N'2025-10-06T10:31:34.1053836' AS DateTime2), CAST(1000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(1000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (24, 3, NULL, NULL, CAST(N'2025-10-06T10:12:25.8958044' AS DateTime2), N'ChoXuLy', N'DaThanhToan', N'ChuyenKhoan', CAST(N'2025-10-06T10:20:25.0662216' AS DateTime2), CAST(1000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(1000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (25, 3, NULL, NULL, CAST(N'2025-10-06T10:24:13.2737979' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(1000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(1000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (26, 3, NULL, NULL, CAST(N'2025-10-06T10:34:32.9284640' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(1000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(1000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (27, 3, NULL, NULL, CAST(N'2025-10-06T10:35:55.2052938' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(2000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(2000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (28, 3, NULL, NULL, CAST(N'2025-10-06T13:58:23.5737559' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(2000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(2000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (29, 3, NULL, NULL, CAST(N'2025-10-06T14:23:54.4042356' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(2000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(2000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (30, 3, NULL, NULL, CAST(N'2025-10-06T14:34:59.9176236' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(2000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(2000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (31, 3, NULL, NULL, CAST(N'2025-10-06T14:49:39.9518931' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(2000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(2000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (32, 3, NULL, NULL, CAST(N'2025-10-06T14:50:44.6041865' AS DateTime2), N'DaGiao', N'ChuaThanhToan', N'Momo', NULL, CAST(2000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(2000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (33, 3, NULL, NULL, CAST(N'2025-10-06T14:52:17.7688579' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(2000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(2000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (34, 3, NULL, NULL, CAST(N'2025-10-06T15:03:21.7288404' AS DateTime2), N'DangPhaChe', N'ChuaThanhToan', N'TienMat', NULL, CAST(18000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(18000.00 AS Decimal(12, 2)), NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (35, 3, NULL, NULL, CAST(N'2025-10-06T20:57:52.1714223' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'TienMat,TienMat', NULL, CAST(19200.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(19200.00 AS Decimal(12, 2)), N'Ship to: Vox Huux Ngu, 123123123, ALO', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (36, 3, NULL, NULL, CAST(N'2025-10-06T20:59:09.7543372' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'TienMat,ChuyenKhoan', NULL, CAST(18000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(18000.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (37, 3, NULL, NULL, CAST(N'2025-10-06T20:59:58.2537678' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'TienMat,ChuyenKhoan', NULL, CAST(18000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(18000.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (38, 3, NULL, NULL, CAST(N'2025-10-06T21:00:13.4542657' AS DateTime2), N'DaGiao', N'ChuaThanhToan', N'ChuyenKhoan,ChuyenKhoan', NULL, CAST(18000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(18000.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (39, 3, NULL, NULL, CAST(N'2025-10-06T21:10:28.5984701' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(18000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(18000.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (40, 3, NULL, NULL, CAST(N'2025-10-06T21:12:10.5708903' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(48000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(48000.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (41, 3, NULL, NULL, CAST(N'2025-10-06T21:13:46.0220595' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(19200.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(19200.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (42, 3, NULL, NULL, CAST(N'2025-10-07T12:50:27.0517082' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(19200.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(19200.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (43, 3, NULL, NULL, CAST(N'2025-10-07T20:00:49.6682655' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-07T20:01:40.0243752' AS DateTime2), CAST(101000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(101000.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (44, 3, NULL, NULL, CAST(N'2025-10-09T09:37:09.4337282' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(40100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(40100.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (45, 3, NULL, NULL, CAST(N'2025-10-09T13:50:09.1701869' AS DateTime2), N'DaHuy', N'DaThanhToan', N'TienMat', CAST(N'2025-10-09T13:53:57.7936539' AS DateTime2), CAST(139900.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(139900.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (46, 3, NULL, NULL, CAST(N'2025-10-09T13:50:56.2696443' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(41100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(41100.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (47, 3, NULL, NULL, CAST(N'2025-10-09T13:51:54.2919527' AS DateTime2), N'DaGiao', N'ChuaThanhToan', N'TienMat', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (48, 3, NULL, NULL, CAST(N'2025-10-09T13:57:10.6108991' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-09T13:57:25.4821550' AS DateTime2), CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (49, 3, 3, NULL, CAST(N'2025-10-09T14:51:31.1331719' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-12T19:35:09.6921244' AS DateTime2), CAST(39000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(39000.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (50, 3, 3, NULL, CAST(N'2025-10-09T19:00:13.7907809' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-12T19:35:04.2039030' AS DateTime2), CAST(71100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(71100.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (51, 3, NULL, NULL, CAST(N'2025-10-09T19:13:10.1786218' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-09T19:14:42.7096245' AS DateTime2), CAST(20300.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(20300.00 AS Decimal(12, 2)), N'Ship to: ', N'Ship', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (52, 3, 3, NULL, CAST(N'2025-10-09T19:59:26.4002392' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'TienMat', NULL, CAST(44000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(44000.00 AS Decimal(12, 2)), N'Giao sớm nha | Ship to: Phan Đình Sáng, 123123123, SPKT', N'Ship', N'SPKT', N'Phan Đình Sáng', N'123123123 ')
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (53, 3, NULL, NULL, CAST(N'2025-10-09T20:00:00.5527774' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-10T15:30:14.9005913' AS DateTime2), CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (54, 3, 3, NULL, CAST(N'2025-10-09T20:28:01.8376135' AS DateTime2), N'DaGiao', N'DaThanhToan', N'ChuyenKhoan', CAST(N'2025-10-09T20:28:36.0028534' AS DateTime2), CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (55, 3, 3, NULL, CAST(N'2025-10-10T14:27:24.8042816' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-10T14:27:36.0125292' AS DateTime2), CAST(135550.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(135550.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (56, 3, 3, NULL, CAST(N'2025-10-10T14:56:52.5754342' AS DateTime2), N'DaGiao', N'DaThanhToan', N'ChuyenKhoan', CAST(N'2025-10-10T14:57:05.5960869' AS DateTime2), CAST(15300.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(15300.00 AS Decimal(12, 2)), N'Ship to: Phan Đình Sáng, 123123123, SPKT', N'Ship', N'SPKT', N'Phan Đình Sáng', N'123123123 ')
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (57, 9, NULL, NULL, CAST(N'2025-10-12T09:07:03.4182350' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-12T09:07:59.5403331' AS DateTime2), CAST(66300.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(66300.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (58, 9, 3, NULL, CAST(N'2025-10-12T09:07:33.2327780' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-12T09:13:53.3517250' AS DateTime2), CAST(45000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(45000.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (59, 9, 5, NULL, CAST(N'2025-10-12T09:14:41.5827331' AS DateTime2), N'DaGiao', N'DaThanhToan', N'TienMat', CAST(N'2025-10-12T09:15:13.3754095' AS DateTime2), CAST(21250.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(21250.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (60, 3, NULL, NULL, CAST(N'2025-10-14T09:29:50.9382641' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'TienMat', NULL, CAST(26300.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(26300.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (61, 3, NULL, NULL, CAST(N'2025-10-14T11:54:14.1445835' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'TienMat', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (62, 3, NULL, NULL, CAST(N'2025-10-15T08:51:19.1101642' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'TienMat', NULL, CAST(45000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(45000.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (63, 3, NULL, NULL, CAST(N'2025-10-15T08:53:17.3826541' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'TienMat', NULL, CAST(21250.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(21250.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (64, 3, NULL, NULL, CAST(N'2025-10-15T09:05:05.8869925' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (65, 3, NULL, NULL, CAST(N'2025-10-15T09:25:59.4045615' AS DateTime2), N'DaHuy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(21250.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(21250.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (66, 3, NULL, NULL, CAST(N'2025-10-16T10:18:14.1470848' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'TienMat', NULL, CAST(62800.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(62800.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (67, 3, NULL, NULL, CAST(N'2025-10-16T10:18:34.9038827' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'TienMat', NULL, CAST(131800.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(131800.00 AS Decimal(12, 2)), N'Ship to: Phan Đình Sáng, 123123123, SPKT', N'Ship', N'SPKT', N'Phan Đình Sáng', N'123123123 ')
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (68, 3, NULL, NULL, CAST(N'2025-10-16T10:23:13.1556596' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'TienMat', NULL, CAST(40650.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(40650.00 AS Decimal(12, 2)), N'ALOAA | Ship to: Phan Đình Sáng, 123123123, SPKT', N'Ship', N'SPKT', N'Phan Đình Sáng', N'123123123 ')
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (69, 3, NULL, NULL, CAST(N'2025-10-17T09:30:48.7175040' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(20900.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(20900.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (70, 3, NULL, NULL, CAST(N'2025-10-17T09:35:04.9543620' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (71, 3, NULL, NULL, CAST(N'2025-10-17T09:39:46.2509413' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (72, 3, NULL, NULL, CAST(N'2025-10-17T09:42:48.8485609' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(21250.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(21250.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (73, 3, NULL, NULL, CAST(N'2025-10-17T09:49:59.4609866' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (74, 3, NULL, NULL, CAST(N'2025-10-17T09:58:17.8581308' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (75, 3, NULL, NULL, CAST(N'2025-10-17T10:04:05.6478187' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (76, 3, NULL, NULL, CAST(N'2025-10-17T10:09:48.0654560' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (77, 3, NULL, NULL, CAST(N'2025-10-17T10:17:04.1901614' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(24000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(24000.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (78, 3, NULL, NULL, CAST(N'2025-10-17T10:20:40.4413875' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(15300.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(15300.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (79, 3, NULL, NULL, CAST(N'2025-10-17T10:29:22.3877797' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (80, 3, NULL, NULL, CAST(N'2025-10-17T10:31:29.6864715' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(21250.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(21250.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (81, 3, NULL, NULL, CAST(N'2025-10-17T10:43:52.2240572' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (82, 3, NULL, NULL, CAST(N'2025-10-17T10:49:56.2734025' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (83, 3, NULL, NULL, CAST(N'2025-10-17T11:02:10.8441920' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (84, 3, NULL, NULL, CAST(N'2025-10-17T11:13:17.9356820' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (85, 3, NULL, NULL, CAST(N'2025-10-17T11:17:52.0229135' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(17100.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(17100.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (86, 3, NULL, NULL, CAST(N'2025-10-17T11:21:26.0038659' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(27000.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(27000.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
INSERT [dbo].[DonHang] ([MaDH], [MaKH], [MaNV], [MaKM], [NgayLap], [TrangThaiDonHang], [PaymentStatus], [PaymentMethod], [PaidAt], [TongHang], [GiamGiaDon], [PhiVanChuyen], [TongThanhToan], [GhiChu], [PhuongThucNhanHang], [DiaChiNhanHang], [TenNguoiNhan], [SDTNguoiNhan]) VALUES (87, 3, NULL, NULL, CAST(N'2025-10-17T11:24:14.7429839' AS DateTime2), N'ChoXuLy', N'ChuaThanhToan', N'ChuyenKhoan', NULL, CAST(21250.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(0.00 AS Decimal(12, 2)), CAST(21250.00 AS Decimal(12, 2)), NULL, N'Pickup', NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[DonHang] OFF
GO
SET IDENTITY_INSERT [dbo].[GioHang] ON 

INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (1, 1, N'ACTIVE', CAST(N'2025-10-03T12:52:38.3587895' AS DateTime2), CAST(N'2025-10-03T12:52:38.3587895' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (2, 2, N'ACTIVE', CAST(N'2025-10-03T12:52:38.3587895' AS DateTime2), CAST(N'2025-10-03T12:52:38.3587895' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (3, 3, N'CHECKED_OUT', CAST(N'2025-10-05T13:28:45.5637914' AS DateTime2), CAST(N'2025-10-05T18:53:56.9845929' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (4, 7, N'ACTIVE', CAST(N'2025-10-05T14:15:00.8795784' AS DateTime2), CAST(N'2025-10-05T14:15:00.8795784' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (5, 3, N'CHECKED_OUT', CAST(N'2025-10-05T18:53:57.0028501' AS DateTime2), CAST(N'2025-10-05T18:56:41.6820835' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (6, 3, N'CHECKED_OUT', CAST(N'2025-10-05T18:56:41.6901206' AS DateTime2), CAST(N'2025-10-05T19:06:16.4767936' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (7, 3, N'CHECKED_OUT', CAST(N'2025-10-05T19:06:16.4862500' AS DateTime2), CAST(N'2025-10-05T19:06:40.4032996' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (8, 3, N'CHECKED_OUT', CAST(N'2025-10-05T19:06:40.4131464' AS DateTime2), CAST(N'2025-10-06T09:17:05.7904790' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (9, 3, N'CHECKED_OUT', CAST(N'2025-10-06T09:17:05.8171281' AS DateTime2), CAST(N'2025-10-06T09:32:37.8892802' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (10, 3, N'CHECKED_OUT', CAST(N'2025-10-06T09:32:37.8974085' AS DateTime2), CAST(N'2025-10-06T09:48:07.6022581' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (11, 3, N'CHECKED_OUT', CAST(N'2025-10-06T09:48:07.6146418' AS DateTime2), CAST(N'2025-10-06T10:03:13.4839292' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (12, 3, N'CHECKED_OUT', CAST(N'2025-10-06T10:03:13.4922788' AS DateTime2), CAST(N'2025-10-06T10:12:11.6555765' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (13, 3, N'CHECKED_OUT', CAST(N'2025-10-06T10:12:11.6653459' AS DateTime2), CAST(N'2025-10-06T10:12:25.9117170' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (14, 3, N'CHECKED_OUT', CAST(N'2025-10-06T10:12:25.9158958' AS DateTime2), CAST(N'2025-10-06T10:24:13.3273775' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (15, 3, N'CHECKED_OUT', CAST(N'2025-10-06T10:24:13.3378516' AS DateTime2), CAST(N'2025-10-06T10:34:32.9638115' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (16, 3, N'CHECKED_OUT', CAST(N'2025-10-06T10:34:32.9722062' AS DateTime2), CAST(N'2025-10-06T10:35:55.2412969' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (17, 3, N'CHECKED_OUT', CAST(N'2025-10-06T10:35:55.2495377' AS DateTime2), CAST(N'2025-10-06T13:58:23.6471375' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (18, 3, N'CHECKED_OUT', CAST(N'2025-10-06T13:58:23.6607827' AS DateTime2), CAST(N'2025-10-06T14:23:54.4573248' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (19, 3, N'CHECKED_OUT', CAST(N'2025-10-06T14:23:54.4652959' AS DateTime2), CAST(N'2025-10-06T14:34:59.9546162' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (20, 3, N'CHECKED_OUT', CAST(N'2025-10-06T14:34:59.9638516' AS DateTime2), CAST(N'2025-10-06T14:49:40.0120577' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (21, 3, N'CHECKED_OUT', CAST(N'2025-10-06T14:49:40.0221940' AS DateTime2), CAST(N'2025-10-06T14:50:44.6640112' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (22, 3, N'CHECKED_OUT', CAST(N'2025-10-06T14:50:44.6765613' AS DateTime2), CAST(N'2025-10-06T14:52:17.8112526' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (23, 3, N'CHECKED_OUT', CAST(N'2025-10-06T14:52:17.8210714' AS DateTime2), CAST(N'2025-10-06T15:03:21.7699562' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (24, 3, N'CHECKED_OUT', CAST(N'2025-10-06T15:03:21.7808856' AS DateTime2), CAST(N'2025-10-06T20:57:52.2474199' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (25, 3, N'CHECKED_OUT', CAST(N'2025-10-06T20:57:52.2640428' AS DateTime2), CAST(N'2025-10-06T20:59:09.7957358' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (26, 3, N'CHECKED_OUT', CAST(N'2025-10-06T20:59:09.8057641' AS DateTime2), CAST(N'2025-10-06T20:59:58.2676235' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (27, 3, N'CHECKED_OUT', CAST(N'2025-10-06T20:59:58.2708718' AS DateTime2), CAST(N'2025-10-06T21:00:13.4915200' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (28, 3, N'CHECKED_OUT', CAST(N'2025-10-06T21:00:13.4992600' AS DateTime2), CAST(N'2025-10-06T21:10:28.6279057' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (29, 3, N'CHECKED_OUT', CAST(N'2025-10-06T21:10:28.6356336' AS DateTime2), CAST(N'2025-10-06T21:12:10.6155626' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (30, 3, N'CHECKED_OUT', CAST(N'2025-10-06T21:12:10.6233895' AS DateTime2), CAST(N'2025-10-06T21:13:46.0548674' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (31, 3, N'CHECKED_OUT', CAST(N'2025-10-06T21:13:46.0621334' AS DateTime2), CAST(N'2025-10-07T12:50:27.1064703' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (32, 3, N'CHECKED_OUT', CAST(N'2025-10-07T12:50:27.1168115' AS DateTime2), CAST(N'2025-10-09T09:37:09.5540446' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (33, 3, N'CHECKED_OUT', CAST(N'2025-10-09T09:37:09.5745351' AS DateTime2), CAST(N'2025-10-09T13:50:56.3126437' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (34, 3, N'CHECKED_OUT', CAST(N'2025-10-09T13:50:56.3281991' AS DateTime2), CAST(N'2025-10-09T13:51:54.3222676' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (35, 3, N'CHECKED_OUT', CAST(N'2025-10-09T13:51:54.3296197' AS DateTime2), CAST(N'2025-10-09T13:57:10.6365819' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (36, 3, N'CHECKED_OUT', CAST(N'2025-10-09T13:57:10.6448694' AS DateTime2), CAST(N'2025-10-09T14:51:31.1882258' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (37, 3, N'CHECKED_OUT', CAST(N'2025-10-09T14:51:31.2012742' AS DateTime2), CAST(N'2025-10-09T19:00:13.8709234' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (38, 3, N'CHECKED_OUT', CAST(N'2025-10-09T19:00:13.8775804' AS DateTime2), CAST(N'2025-10-09T19:13:10.2051640' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (39, 3, N'CHECKED_OUT', CAST(N'2025-10-09T19:13:10.2072677' AS DateTime2), CAST(N'2025-10-09T19:59:26.4660768' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (40, 3, N'CHECKED_OUT', CAST(N'2025-10-09T19:59:26.4829811' AS DateTime2), CAST(N'2025-10-09T20:00:00.5858727' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (41, 3, N'CHECKED_OUT', CAST(N'2025-10-09T20:00:00.5933276' AS DateTime2), CAST(N'2025-10-09T20:28:01.8698433' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (42, 3, N'CHECKED_OUT', CAST(N'2025-10-09T20:28:01.8784935' AS DateTime2), CAST(N'2025-10-10T14:27:24.9204637' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (43, 3, N'CHECKED_OUT', CAST(N'2025-10-10T14:27:24.9338404' AS DateTime2), CAST(N'2025-10-10T14:56:52.6205418' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (44, 3, N'CHECKED_OUT', CAST(N'2025-10-10T14:56:52.6287922' AS DateTime2), CAST(N'2025-10-14T11:54:14.1883967' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (45, 9, N'CHECKED_OUT', CAST(N'2025-10-12T09:06:46.0785974' AS DateTime2), CAST(N'2025-10-12T09:07:03.4722336' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (46, 9, N'CHECKED_OUT', CAST(N'2025-10-12T09:07:03.4829199' AS DateTime2), CAST(N'2025-10-12T09:07:33.2578198' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (47, 9, N'CHECKED_OUT', CAST(N'2025-10-12T09:07:33.2659628' AS DateTime2), CAST(N'2025-10-12T09:14:41.6103334' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (48, 9, N'ACTIVE', CAST(N'2025-10-12T09:14:41.6178339' AS DateTime2), CAST(N'2025-10-12T09:14:41.6178339' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (49, 3, N'CHECKED_OUT', CAST(N'2025-10-14T11:54:14.2075797' AS DateTime2), CAST(N'2025-10-15T08:51:19.2064063' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (50, 3, N'CHECKED_OUT', CAST(N'2025-10-15T08:51:19.2236816' AS DateTime2), CAST(N'2025-10-15T08:53:17.4369485' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (51, 3, N'CHECKED_OUT', CAST(N'2025-10-15T08:53:17.4628706' AS DateTime2), CAST(N'2025-10-15T09:05:05.9209686' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (52, 3, N'CHECKED_OUT', CAST(N'2025-10-15T09:05:05.9275632' AS DateTime2), CAST(N'2025-10-15T09:25:59.4369244' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (53, 3, N'CHECKED_OUT', CAST(N'2025-10-15T09:25:59.4449785' AS DateTime2), CAST(N'2025-10-16T10:23:13.2053451' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (54, 3, N'CHECKED_OUT', CAST(N'2025-10-16T10:23:13.2239160' AS DateTime2), CAST(N'2025-10-17T09:30:48.7749084' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (55, 3, N'CHECKED_OUT', CAST(N'2025-10-17T09:30:48.7959087' AS DateTime2), CAST(N'2025-10-17T09:35:04.9894756' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (56, 3, N'CHECKED_OUT', CAST(N'2025-10-17T09:35:04.9989701' AS DateTime2), CAST(N'2025-10-17T09:39:46.2852014' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (57, 3, N'CHECKED_OUT', CAST(N'2025-10-17T09:39:46.2937957' AS DateTime2), CAST(N'2025-10-17T09:42:48.8782612' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (58, 3, N'CHECKED_OUT', CAST(N'2025-10-17T09:42:48.8862742' AS DateTime2), CAST(N'2025-10-17T09:49:59.4913372' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (59, 3, N'CHECKED_OUT', CAST(N'2025-10-17T09:49:59.4989872' AS DateTime2), CAST(N'2025-10-17T09:58:17.8924706' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (60, 3, N'CHECKED_OUT', CAST(N'2025-10-17T09:58:17.9022609' AS DateTime2), CAST(N'2025-10-17T10:04:05.6810632' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (61, 3, N'CHECKED_OUT', CAST(N'2025-10-17T10:04:05.6895816' AS DateTime2), CAST(N'2025-10-17T10:09:48.0992167' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (62, 3, N'CHECKED_OUT', CAST(N'2025-10-17T10:09:48.1058412' AS DateTime2), CAST(N'2025-10-17T10:17:04.2230418' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (63, 3, N'CHECKED_OUT', CAST(N'2025-10-17T10:17:04.2309899' AS DateTime2), CAST(N'2025-10-17T10:20:40.4911079' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (64, 3, N'CHECKED_OUT', CAST(N'2025-10-17T10:20:40.5033472' AS DateTime2), CAST(N'2025-10-17T10:29:22.4192199' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (65, 3, N'CHECKED_OUT', CAST(N'2025-10-17T10:29:22.4278917' AS DateTime2), CAST(N'2025-10-17T10:31:29.7190006' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (66, 3, N'CHECKED_OUT', CAST(N'2025-10-17T10:31:29.7278428' AS DateTime2), CAST(N'2025-10-17T10:43:52.2615518' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (67, 3, N'CHECKED_OUT', CAST(N'2025-10-17T10:43:52.2704256' AS DateTime2), CAST(N'2025-10-17T10:49:56.3058813' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (68, 3, N'CHECKED_OUT', CAST(N'2025-10-17T10:49:56.3136197' AS DateTime2), CAST(N'2025-10-17T11:02:10.8798249' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (69, 3, N'CHECKED_OUT', CAST(N'2025-10-17T11:02:10.8897891' AS DateTime2), CAST(N'2025-10-17T11:13:17.9746022' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (70, 3, N'CHECKED_OUT', CAST(N'2025-10-17T11:13:17.9819467' AS DateTime2), CAST(N'2025-10-17T11:17:52.0599782' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (71, 3, N'CHECKED_OUT', CAST(N'2025-10-17T11:17:52.0694479' AS DateTime2), CAST(N'2025-10-17T11:21:26.0461644' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (72, 3, N'CHECKED_OUT', CAST(N'2025-10-17T11:21:26.0547809' AS DateTime2), CAST(N'2025-10-17T11:24:14.7767130' AS DateTime2))
INSERT [dbo].[GioHang] ([MaGH], [MaKH], [TrangThai], [CreatedAt], [UpdatedAt]) VALUES (73, 3, N'ACTIVE', CAST(N'2025-10-17T11:24:14.7849728' AS DateTime2), CAST(N'2025-10-17T11:24:14.7849728' AS DateTime2))
SET IDENTITY_INSERT [dbo].[GioHang] OFF
GO
SET IDENTITY_INSERT [dbo].[GioHangCT] ON 

INSERT [dbo].[GioHangCT] ([MaCTGH], [MaGH], [MaBT], [SoLuong], [DonGia], [ThanhTien], [GhiChu]) VALUES (1, 1, 2, 2, CAST(25000.00 AS Decimal(10, 2)), CAST(50000.00 AS Decimal(10, 2)), N'Ít đá')
INSERT [dbo].[GioHangCT] ([MaCTGH], [MaGH], [MaBT], [SoLuong], [DonGia], [ThanhTien], [GhiChu]) VALUES (2, 1, 5, 1, CAST(27000.00 AS Decimal(10, 2)), CAST(27000.00 AS Decimal(10, 2)), NULL)
INSERT [dbo].[GioHangCT] ([MaCTGH], [MaGH], [MaBT], [SoLuong], [DonGia], [ThanhTien], [GhiChu]) VALUES (12, 4, 11, 1, CAST(22000.00 AS Decimal(10, 2)), CAST(26000.00 AS Decimal(10, 2)), N'Đường: Bình thường; Đá: Bình thường')
SET IDENTITY_INSERT [dbo].[GioHangCT] OFF
GO
INSERT [dbo].[GioHangCT_Topping] ([MaCTGH], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (1, 1, 1, CAST(5000.00 AS Decimal(10, 2)), CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[GioHangCT_Topping] ([MaCTGH], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (1, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
INSERT [dbo].[GioHangCT_Topping] ([MaCTGH], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (2, 2, 1, CAST(6000.00 AS Decimal(10, 2)), CAST(6000.00 AS Decimal(10, 2)))
INSERT [dbo].[GioHangCT_Topping] ([MaCTGH], [MaTopping], [SoLuong], [DonGia], [ThanhTien]) VALUES (12, 3, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(4000.00 AS Decimal(10, 2)))
GO
SET IDENTITY_INSERT [dbo].[KhachHang] ON 

INSERT [dbo].[KhachHang] ([MaKH], [Username], [MatKhauHash], [Email], [TenKH], [SoDienThoai], [TrangThai], [AnhDaiDien]) VALUES (1, N'khanhnguyen', N'$2a$10$ByO4AsTPqG2XVSOA8FKd9OJj1QuX6SgW.uNu5EcwRUT3022QY13Jq', N'khanh@example.com', N'Nguyễn Khánh', N'0912345678', 1, NULL)
INSERT [dbo].[KhachHang] ([MaKH], [Username], [MatKhauHash], [Email], [TenKH], [SoDienThoai], [TrangThai], [AnhDaiDien]) VALUES (2, N'linhpham', N'$2a$10$6feKgfK/m6WIgdaAe5tWD.4Ztimo7qvcaOMy9AgrOap1.pgbJw8uC', N'linh@example.com', N'Phạm Linh', N'0987654321', 1, NULL)
INSERT [dbo].[KhachHang] ([MaKH], [Username], [MatKhauHash], [Email], [TenKH], [SoDienThoai], [TrangThai], [AnhDaiDien]) VALUES (3, N'trac@gmail.com', N'$2a$10$2hoosJv7VngsRrzIjBnlNOgbM6.TRsru3iz3ekIJplwy53K1ry.oC', N'trackhoa1105@gmail.com', N'Phan Đình Sáng', N'123123123', 1, NULL)
INSERT [dbo].[KhachHang] ([MaKH], [Username], [MatKhauHash], [Email], [TenKH], [SoDienThoai], [TrangThai], [AnhDaiDien]) VALUES (4, N'trac123@gmail.com', N'$2a$10$.P.bQz3zCX8Y/YzHAJKhw.HfmqNX0MYddQZOA6wWD/5cQuYYswHm6', N'trackhoa1105@gmamil.ccom', N'Trác Ngọc Đăng', N'123123', 1, NULL)
INSERT [dbo].[KhachHang] ([MaKH], [Username], [MatKhauHash], [Email], [TenKH], [SoDienThoai], [TrangThai], [AnhDaiDien]) VALUES (5, N'trackhoa2005@gmail.com', N'$2a$10$DJllagh0Z.BHh9SsimcJV.4qutdoTgV/P4.9Sjy1EB7NZnUJgmFhe', N'trackhoa2005@gmail.com', N'Trác Ngọc Đăng An', N'666666', 1, NULL)
INSERT [dbo].[KhachHang] ([MaKH], [Username], [MatKhauHash], [Email], [TenKH], [SoDienThoai], [TrangThai], [AnhDaiDien]) VALUES (6, N'boss', N'$2a$10$3Aj4e223jyxV6PpU/FNRHOWV3wCpTCNqlZDoAli1psrDnK9fShNOK', N'boss@alotra.com', N'AloTra Administrator', N'0900000000', 1, NULL)
INSERT [dbo].[KhachHang] ([MaKH], [Username], [MatKhauHash], [Email], [TenKH], [SoDienThoai], [TrangThai], [AnhDaiDien]) VALUES (7, N'tracngockhoa', N'$2a$10$fAzOaq/25QFSl99CDH0jtufMkaU2Lw8XkH/fK6mJY1ZZ/DYwm5QyC', N'ngoctrac@gmail.com', N'Ngọc Trác', N'123123123222', 1, NULL)
INSERT [dbo].[KhachHang] ([MaKH], [Username], [MatKhauHash], [Email], [TenKH], [SoDienThoai], [TrangThai], [AnhDaiDien]) VALUES (9, N'phuochoa2021vg@gmail.com', N'$2a$10$X6svdgFF0feS5SY4pALBjeFy.J.932zcqSqGVkIdapL.1PH2xavbm', N'phuochoa2021vg@gmail.com', N'Hồng Phước Hòa', N'12512235', 1, NULL)
SET IDENTITY_INSERT [dbo].[KhachHang] OFF
GO
INSERT [dbo].[KhuyenMaiSanPham] ([MaKM], [MaSP], [PhanTramGiam]) VALUES (1, 3, 15)
INSERT [dbo].[KhuyenMaiSanPham] ([MaKM], [MaSP], [PhanTramGiam]) VALUES (1, 4, 5)
INSERT [dbo].[KhuyenMaiSanPham] ([MaKM], [MaSP], [PhanTramGiam]) VALUES (1, 8, 15)
INSERT [dbo].[KhuyenMaiSanPham] ([MaKM], [MaSP], [PhanTramGiam]) VALUES (5, 1, 10)
GO
SET IDENTITY_INSERT [dbo].[NhanVien] ON 

INSERT [dbo].[NhanVien] ([MaNV], [Username], [MatKhauHash], [Email], [TenNV], [VaiTro], [SoDienThoai], [TrangThai], [AnhDaiDien], [DeletedAt]) VALUES (1, N'admin', N'$2a$10$A1vUWQEQC7l2LPyy1jlnl.5c35zfBvWydCrMEjlI5WkhxhK4k96z2', N'admin@trasua.com', N'Nguyễn Quản Lý', 2, N'0900000001', 1, NULL, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [Username], [MatKhauHash], [Email], [TenNV], [VaiTro], [SoDienThoai], [TrangThai], [AnhDaiDien], [DeletedAt]) VALUES (3, N'phuochoa', N'$2a$10$iFN2S/EvbUob3tnGFAvLo.wNsHwQWDQcKCAyKTGXEbNdeZ/DSFImm', N'vietkv@gmail.com', N'Hong Phuoc Hoa', 2, N'0999888777', 1, NULL, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [Username], [MatKhauHash], [Email], [TenNV], [VaiTro], [SoDienThoai], [TrangThai], [AnhDaiDien], [DeletedAt]) VALUES (4, N'boss', N'$2a$10$LwJhmokLZri0WHXVLXkJSOuauIoomX2YgxmPmrWe7SRkfhTZ1vnFy', N'johnn@gmail.com', N'John Nguyễn', 2, N'123422356', 0, NULL, CAST(N'2025-10-12T09:04:27.0560740' AS DateTime2))
INSERT [dbo].[NhanVien] ([MaNV], [Username], [MatKhauHash], [Email], [TenNV], [VaiTro], [SoDienThoai], [TrangThai], [AnhDaiDien], [DeletedAt]) VALUES (5, N'noname', N'$2a$10$HE0ramT17haI/CbgeQlNWuQeEHuN7djqOrFEASqfEEShfRmFRJJOS', N'khoadang@gmail.com', N'Khoa Kim', 2, N'5246624422', 1, NULL, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [Username], [MatKhauHash], [Email], [TenNV], [VaiTro], [SoDienThoai], [TrangThai], [AnhDaiDien], [DeletedAt]) VALUES (6, N'trac', N'$2a$10$lRiTQzLabTjUvN9kCkbQmu2Nj2WdTpAl2jZH4o8SKHACswAdUqFb.', N'trackhoa1105@gmail.com', N'Trác Ngọc Đăng', 2, N'12312345', 0, NULL, CAST(N'2025-10-14T08:30:31.8971470' AS DateTime2))
INSERT [dbo].[NhanVien] ([MaNV], [Username], [MatKhauHash], [Email], [TenNV], [VaiTro], [SoDienThoai], [TrangThai], [AnhDaiDien], [DeletedAt]) VALUES (7, N'boss2', N'$2a$10$KJMHLTlzrwDnaKbUvrjqHum4G1yW8uudo4n4/rQLZYaronwd4TXr6', N'boss2@alotra.com', N'bosAlo2', 1, N'545773322234', 1, NULL, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [Username], [MatKhauHash], [Email], [TenNV], [VaiTro], [SoDienThoai], [TrangThai], [AnhDaiDien], [DeletedAt]) VALUES (8, N'đâsd', N'$2a$10$fplATezKXOAV.DgKvyzsP.omEg4kcW.Cg9xxy.OS2NUG4aqw0wwhG', N'll@gmail.com', N'ádasdasd', 2, N'', 0, NULL, CAST(N'2025-10-14T10:24:45.2201980' AS DateTime2))
INSERT [dbo].[NhanVien] ([MaNV], [Username], [MatKhauHash], [Email], [TenNV], [VaiTro], [SoDienThoai], [TrangThai], [AnhDaiDien], [DeletedAt]) VALUES (10, N'nva', N'$2a$10$RzQ9R5EnDXv6YemRlkV00Ok6yMIksO.dwYIvbsfNNxtEyVWi.OuJq', N'nv.a@alotra.com', N'Nguyễn Văn A', 2, N'0869697790', 1, NULL, NULL)
SET IDENTITY_INSERT [dbo].[NhanVien] OFF
GO
SET IDENTITY_INSERT [dbo].[SanPham] ON 

INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (1, 1, N'Trà Sữa Truyền Thống', N'Trà s?a v? truy?n th?ng', 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761449448/Tr%C3%A0_s%E1%BB%AFa_z2ihuq.png', NULL)
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (2, 1, N'Trà Sữa Matcha', N'Trà s?a v? matcha xanh', 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761449668/Tr%C3%A0_s%E1%BB%AFa_matcha_uwnvw6.png', NULL)
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (3, 2, N'Trà Đào Cam Sả', N'Trà dào cam s? thom mát', 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761449752/Tr%C3%A0_%C4%91%C3%A0o_cam_s%E1%BA%A3_benjcb.png', NULL)
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (4, 3, N'Nước ép cam', N'Nước ép cam tươi

', 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761449818/N%C6%B0%E1%BB%9Bc_%C3%A9p_cam_oyn7hh.png', NULL)
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (5, 5, N'Cold Drew trái cây', N'Cà phê d?u nh?, mát l?nh, có huong trái cây', 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761449850/Cold_Drew_tr%C3%A1i_c%C3%A2y_kecbh2.png', NULL)
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (7, 5, N'Americano', N'Cà phê nh?t nh?o', 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761449892/Americano_ywnwmn.png', NULL)
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (8, 2, N'Trà đào', N'Trà olong v? dào', 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761449960/%C3%94_long_%C4%91%C3%A0o_wwtnis.png', NULL)
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (9, 1, N'test thanh toán', N'1k', 0, N'', CAST(N'2025-10-09T19:41:12.9298440' AS DateTime2))
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (10, 1, N'Trà sữa rang', N'Ð?m', 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761449448/Tr%C3%A0_s%E1%BB%AFa_z2ihuq.png', NULL)
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (11, 3, N'Nước ép dưa hấu', N'Dưa hấu nè!', 0, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761450009/N%C6%B0%E1%BB%9Bc_%C3%A9p_d%C6%B0a_h%E1%BA%A5u_yc78t2.png', CAST(N'2025-10-14T09:14:23.8933920' AS DateTime2))
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (12, 1, N'Ma Nhan Nam', N'ssas', 0, N'', CAST(N'2025-10-14T09:07:29.1734820' AS DateTime2))
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (13, 1, N'[[[[[', N'', 0, N'', CAST(N'2025-10-14T09:53:59.9498230' AS DateTime2))
INSERT [dbo].[SanPham] ([MaSP], [MaDM], [TenSP], [MoTa], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (14, 1, N'test', N'123', 0, N'', CAST(N'2025-10-14T12:08:40.1126730' AS DateTime2))
SET IDENTITY_INSERT [dbo].[SanPham] OFF
GO
SET IDENTITY_INSERT [dbo].[SizeSanPham] ON 

INSERT [dbo].[SizeSanPham] ([MaSize], [TenSize], [TrangThai]) VALUES (1, N'S', 1)
INSERT [dbo].[SizeSanPham] ([MaSize], [TenSize], [TrangThai]) VALUES (2, N'M', 1)
INSERT [dbo].[SizeSanPham] ([MaSize], [TenSize], [TrangThai]) VALUES (3, N'L', 1)
SET IDENTITY_INSERT [dbo].[SizeSanPham] OFF
GO
SET IDENTITY_INSERT [dbo].[SuKienKhuyenMai] ON 

INSERT [dbo].[SuKienKhuyenMai] ([MaKM], [TenSuKien], [MoTa], [NgayBD], [NgayKT], [TrangThai], [UrlAnh], [LuotXem], [DeletedAt]) VALUES (1, N'Khuyến mãi mùa tựu trường', N'Mùa tựu trường, giảm giá đậm sâu', CAST(N'2025-08-01' AS Date), CAST(N'2025-10-01' AS Date), 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761450451/Khuy%E1%BA%BFn_m%C3%A3i_b2xcow.jpg', 4, NULL)
INSERT [dbo].[SuKienKhuyenMai] ([MaKM], [TenSuKien], [MoTa], [NgayBD], [NgayKT], [TrangThai], [UrlAnh], [LuotXem], [DeletedAt]) VALUES (2, N'Sinh nhật cửa hàng', N'Ưu đãi toàn menu
', CAST(N'2025-10-01' AS Date), CAST(N'2025-10-10' AS Date), 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761450451/Khuy%E1%BA%BFn_m%C3%A3i_b2xcow.jpg', 1, NULL)
INSERT [dbo].[SuKienKhuyenMai] ([MaKM], [TenSuKien], [MoTa], [NgayBD], [NgayKT], [TrangThai], [UrlAnh], [LuotXem], [DeletedAt]) VALUES (5, N'Khuyến mãi sốc mừng 20/10', N'Khuyến mãi 20/10!', CAST(N'2025-10-01' AS Date), CAST(N'2025-10-20' AS Date), 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761450451/Khuy%E1%BA%BFn_m%C3%A3i_b2xcow.jpg', 1, NULL)
SET IDENTITY_INSERT [dbo].[SuKienKhuyenMai] OFF
GO
SET IDENTITY_INSERT [dbo].[Topping] ON 

INSERT [dbo].[Topping] ([MaTopping], [TenTopping], [GiaThem], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (1, N'Trân châu đen', CAST(5000.00 AS Decimal(10, 2)), 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761450324/Tran-Chau-Den_uyp7ow.png', NULL)
INSERT [dbo].[Topping] ([MaTopping], [TenTopping], [GiaThem], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (2, N'Trân châu trắng', CAST(6000.00 AS Decimal(10, 2)), 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761450365/tr%C3%A2n_ch%C3%A2u_tr%E1%BA%AFng_nskmtg.jpg', NULL)
INSERT [dbo].[Topping] ([MaTopping], [TenTopping], [GiaThem], [TrangThai], [UrlAnh], [DeletedAt]) VALUES (3, N'Thạch dừa', CAST(4000.00 AS Decimal(10, 2)), 1, N'https://res.cloudinary.com/dvxxd3vox/image/upload/v1761450391/th%E1%BA%A1ch_d%E1%BB%ABa_ng5tks.jpg', NULL)
SET IDENTITY_INSERT [dbo].[Topping] OFF
GO
/****** Object:  Index [UQ_BienThe_Unique]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[BienTheSanPham] ADD  CONSTRAINT [UQ_BienThe_Unique] UNIQUE NONCLUSTERED 
(
	[MaSP] ASC,
	[MaSize] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ_DanhGia_OnePerLine]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[DanhGia] ADD  CONSTRAINT [UQ_DanhGia_OnePerLine] UNIQUE NONCLUSTERED 
(
	[MaKH] ASC,
	[MaCT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__DanhMucS__4CF9655957F1B37E]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[DanhMucSanPham] ADD UNIQUE NONCLUSTERED 
(
	[TenDM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UX_GioHang_Active_OnePerKH]    Script Date: 10/17/2025 11:31:10 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UX_GioHang_Active_OnePerKH] ON [dbo].[GioHang]
(
	[MaKH] ASC
)
WHERE ([TrangThai]=N'ACTIVE')
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__KhachHan__0389B7BDBDB72997]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[KhachHang] ADD UNIQUE NONCLUSTERED 
(
	[SoDienThoai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__KhachHan__536C85E45C73E63D]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[KhachHang] ADD UNIQUE NONCLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__KhachHan__A9D10534ACF950C4]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[KhachHang] ADD UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__0389B7BD49C150A7]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[SoDienThoai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__536C85E4EA1AC870]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__A9D1053469A1FD9F]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__SizeSanP__C86AACB9504B1C8E]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[SizeSanPham] ADD UNIQUE NONCLUSTERED 
(
	[TenSize] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Topping__F113523A8484DBD9]    Script Date: 10/17/2025 11:31:10 AM ******/
ALTER TABLE [dbo].[Topping] ADD UNIQUE NONCLUSTERED 
(
	[TenTopping] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BienTheSanPham] ADD  DEFAULT ((1)) FOR [TrangThai]
GO
ALTER TABLE [dbo].[CTDonHang] ADD  DEFAULT ((0)) FOR [GiamGiaDong]
GO
ALTER TABLE [dbo].[CTDonHang_Topping] ADD  DEFAULT ((1)) FOR [SoLuong]
GO
ALTER TABLE [dbo].[DanhGia] ADD  DEFAULT (sysutcdatetime()) FOR [NgayDG]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT (sysutcdatetime()) FOR [NgayLap]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT (N'ChoXuLy') FOR [TrangThaiDonHang]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT (N'ChuaThanhToan') FOR [PaymentStatus]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ((0)) FOR [TongHang]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ((0)) FOR [GiamGiaDon]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ((0)) FOR [PhiVanChuyen]
GO
ALTER TABLE [dbo].[DonHang] ADD  DEFAULT ((0)) FOR [TongThanhToan]
GO
ALTER TABLE [dbo].[GioHang] ADD  DEFAULT (N'ACTIVE') FOR [TrangThai]
GO
ALTER TABLE [dbo].[GioHang] ADD  DEFAULT (sysutcdatetime()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[GioHang] ADD  DEFAULT (sysutcdatetime()) FOR [UpdatedAt]
GO
ALTER TABLE [dbo].[GioHangCT_Topping] ADD  DEFAULT ((1)) FOR [SoLuong]
GO
ALTER TABLE [dbo].[KhachHang] ADD  DEFAULT ((1)) FOR [TrangThai]
GO
ALTER TABLE [dbo].[NhanVien] ADD  DEFAULT ((1)) FOR [TrangThai]
GO
ALTER TABLE [dbo].[SanPham] ADD  DEFAULT ((1)) FOR [TrangThai]
GO
ALTER TABLE [dbo].[SizeSanPham] ADD  DEFAULT ((1)) FOR [TrangThai]
GO
ALTER TABLE [dbo].[SuKienKhuyenMai] ADD  CONSTRAINT [DF__SuKienKhu__Trang__5165187F]  DEFAULT ((1)) FOR [TrangThai]
GO
ALTER TABLE [dbo].[Topping] ADD  DEFAULT ((1)) FOR [TrangThai]
GO
ALTER TABLE [dbo].[BienTheSanPham]  WITH CHECK ADD  CONSTRAINT [FK_BienThe_SanPham] FOREIGN KEY([MaSP])
REFERENCES [dbo].[SanPham] ([MaSP])
GO
ALTER TABLE [dbo].[BienTheSanPham] CHECK CONSTRAINT [FK_BienThe_SanPham]
GO
ALTER TABLE [dbo].[BienTheSanPham]  WITH CHECK ADD  CONSTRAINT [FK_BienThe_Size] FOREIGN KEY([MaSize])
REFERENCES [dbo].[SizeSanPham] ([MaSize])
GO
ALTER TABLE [dbo].[BienTheSanPham] CHECK CONSTRAINT [FK_BienThe_Size]
GO
ALTER TABLE [dbo].[CTDonHang]  WITH CHECK ADD  CONSTRAINT [FK_CTDH_BT] FOREIGN KEY([MaBT])
REFERENCES [dbo].[BienTheSanPham] ([MaBT])
GO
ALTER TABLE [dbo].[CTDonHang] CHECK CONSTRAINT [FK_CTDH_BT]
GO
ALTER TABLE [dbo].[CTDonHang]  WITH CHECK ADD  CONSTRAINT [FK_CTDH_DH] FOREIGN KEY([MaDH])
REFERENCES [dbo].[DonHang] ([MaDH])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CTDonHang] CHECK CONSTRAINT [FK_CTDH_DH]
GO
ALTER TABLE [dbo].[CTDonHang_Topping]  WITH CHECK ADD  CONSTRAINT [FK_CTDHT_CTDH] FOREIGN KEY([MaCT])
REFERENCES [dbo].[CTDonHang] ([MaCT])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CTDonHang_Topping] CHECK CONSTRAINT [FK_CTDHT_CTDH]
GO
ALTER TABLE [dbo].[CTDonHang_Topping]  WITH CHECK ADD  CONSTRAINT [FK_CTDHT_T] FOREIGN KEY([MaTopping])
REFERENCES [dbo].[Topping] ([MaTopping])
GO
ALTER TABLE [dbo].[CTDonHang_Topping] CHECK CONSTRAINT [FK_CTDHT_T]
GO
ALTER TABLE [dbo].[DanhGia]  WITH CHECK ADD  CONSTRAINT [FK_DG_CT] FOREIGN KEY([MaCT])
REFERENCES [dbo].[CTDonHang] ([MaCT])
GO
ALTER TABLE [dbo].[DanhGia] CHECK CONSTRAINT [FK_DG_CT]
GO
ALTER TABLE [dbo].[DanhGia]  WITH CHECK ADD  CONSTRAINT [FK_DG_KH] FOREIGN KEY([MaKH])
REFERENCES [dbo].[KhachHang] ([MaKH])
GO
ALTER TABLE [dbo].[DanhGia] CHECK CONSTRAINT [FK_DG_KH]
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD  CONSTRAINT [FK_DH_KH] FOREIGN KEY([MaKH])
REFERENCES [dbo].[KhachHang] ([MaKH])
GO
ALTER TABLE [dbo].[DonHang] CHECK CONSTRAINT [FK_DH_KH]
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD  CONSTRAINT [FK_DH_KM] FOREIGN KEY([MaKM])
REFERENCES [dbo].[SuKienKhuyenMai] ([MaKM])
GO
ALTER TABLE [dbo].[DonHang] CHECK CONSTRAINT [FK_DH_KM]
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD  CONSTRAINT [FK_DH_NV] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[DonHang] CHECK CONSTRAINT [FK_DH_NV]
GO
ALTER TABLE [dbo].[GioHang]  WITH CHECK ADD  CONSTRAINT [FK_GH_KH] FOREIGN KEY([MaKH])
REFERENCES [dbo].[KhachHang] ([MaKH])
GO
ALTER TABLE [dbo].[GioHang] CHECK CONSTRAINT [FK_GH_KH]
GO
ALTER TABLE [dbo].[GioHangCT]  WITH CHECK ADD  CONSTRAINT [FK_GHCT_BT] FOREIGN KEY([MaBT])
REFERENCES [dbo].[BienTheSanPham] ([MaBT])
GO
ALTER TABLE [dbo].[GioHangCT] CHECK CONSTRAINT [FK_GHCT_BT]
GO
ALTER TABLE [dbo].[GioHangCT]  WITH CHECK ADD  CONSTRAINT [FK_GHCT_GH] FOREIGN KEY([MaGH])
REFERENCES [dbo].[GioHang] ([MaGH])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[GioHangCT] CHECK CONSTRAINT [FK_GHCT_GH]
GO
ALTER TABLE [dbo].[GioHangCT_Topping]  WITH CHECK ADD  CONSTRAINT [FK_GHCTT_GHCT] FOREIGN KEY([MaCTGH])
REFERENCES [dbo].[GioHangCT] ([MaCTGH])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[GioHangCT_Topping] CHECK CONSTRAINT [FK_GHCTT_GHCT]
GO
ALTER TABLE [dbo].[GioHangCT_Topping]  WITH CHECK ADD  CONSTRAINT [FK_GHCTT_T] FOREIGN KEY([MaTopping])
REFERENCES [dbo].[Topping] ([MaTopping])
GO
ALTER TABLE [dbo].[GioHangCT_Topping] CHECK CONSTRAINT [FK_GHCTT_T]
GO
ALTER TABLE [dbo].[KhuyenMaiSanPham]  WITH CHECK ADD  CONSTRAINT [FK_KMSP_KM] FOREIGN KEY([MaKM])
REFERENCES [dbo].[SuKienKhuyenMai] ([MaKM])
GO
ALTER TABLE [dbo].[KhuyenMaiSanPham] CHECK CONSTRAINT [FK_KMSP_KM]
GO
ALTER TABLE [dbo].[KhuyenMaiSanPham]  WITH CHECK ADD  CONSTRAINT [FK_KMSP_SP] FOREIGN KEY([MaSP])
REFERENCES [dbo].[SanPham] ([MaSP])
GO
ALTER TABLE [dbo].[KhuyenMaiSanPham] CHECK CONSTRAINT [FK_KMSP_SP]
GO
ALTER TABLE [dbo].[OtpCodes]  WITH CHECK ADD FOREIGN KEY([KhachHangId])
REFERENCES [dbo].[KhachHang] ([MaKH])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ProductMedia]  WITH CHECK ADD  CONSTRAINT [FKja8o0hbvf4ugyqx0rc4xkm9h5] FOREIGN KEY([ProductId])
REFERENCES [dbo].[SanPham] ([MaSP])
GO
ALTER TABLE [dbo].[ProductMedia] CHECK CONSTRAINT [FKja8o0hbvf4ugyqx0rc4xkm9h5]
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD  CONSTRAINT [FK_SanPham_DanhMuc] FOREIGN KEY([MaDM])
REFERENCES [dbo].[DanhMucSanPham] ([MaDM])
GO
ALTER TABLE [dbo].[SanPham] CHECK CONSTRAINT [FK_SanPham_DanhMuc]
GO
ALTER TABLE [dbo].[BienTheSanPham]  WITH CHECK ADD CHECK  (([GiaBan]>(0)))
GO
ALTER TABLE [dbo].[BienTheSanPham]  WITH CHECK ADD CHECK  (([TrangThai]=(1) OR [TrangThai]=(0)))
GO
ALTER TABLE [dbo].[CTDonHang]  WITH CHECK ADD CHECK  (([DonGia]>=(0)))
GO
ALTER TABLE [dbo].[CTDonHang]  WITH CHECK ADD CHECK  (([GiamGiaDong]>=(0)))
GO
ALTER TABLE [dbo].[CTDonHang]  WITH CHECK ADD CHECK  (([SoLuong]>(0)))
GO
ALTER TABLE [dbo].[CTDonHang]  WITH CHECK ADD CHECK  (([ThanhTien]>=(0)))
GO
ALTER TABLE [dbo].[CTDonHang_Topping]  WITH CHECK ADD CHECK  (([DonGia]>=(0)))
GO
ALTER TABLE [dbo].[CTDonHang_Topping]  WITH CHECK ADD CHECK  (([SoLuong]>(0)))
GO
ALTER TABLE [dbo].[CTDonHang_Topping]  WITH CHECK ADD CHECK  (([ThanhTien]>=(0)))
GO
ALTER TABLE [dbo].[DanhGia]  WITH CHECK ADD CHECK  (([SoSao]>=(1) AND [SoSao]<=(5)))
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD CHECK  (([GiamGiaDon]>=(0)))
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD CHECK  (([PaymentStatus]=N'DaThanhToan' OR [PaymentStatus]=N'ChuaThanhToan'))
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD CHECK  (([PhiVanChuyen]>=(0)))
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD CHECK  (([TongHang]>=(0)))
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD CHECK  (([TongThanhToan]>=(0)))
GO
ALTER TABLE [dbo].[DonHang]  WITH CHECK ADD CHECK  (([TrangThaiDonHang]=N'DaHuy' OR [TrangThaiDonHang]=N'DaGiao' OR [TrangThaiDonHang]=N'DangGiao' OR [TrangThaiDonHang]=N'DangPhaChe' OR [TrangThaiDonHang]=N'ChoXuLy'))
GO
ALTER TABLE [dbo].[GioHang]  WITH CHECK ADD CHECK  (([TrangThai]=N'CANCELLED' OR [TrangThai]=N'CHECKED_OUT' OR [TrangThai]=N'ACTIVE'))
GO
ALTER TABLE [dbo].[GioHangCT]  WITH CHECK ADD CHECK  (([DonGia]>=(0)))
GO
ALTER TABLE [dbo].[GioHangCT]  WITH CHECK ADD CHECK  (([SoLuong]>(0)))
GO
ALTER TABLE [dbo].[GioHangCT]  WITH CHECK ADD CHECK  (([ThanhTien]>=(0)))
GO
ALTER TABLE [dbo].[GioHangCT_Topping]  WITH CHECK ADD CHECK  (([DonGia]>=(0)))
GO
ALTER TABLE [dbo].[GioHangCT_Topping]  WITH CHECK ADD CHECK  (([SoLuong]>(0)))
GO
ALTER TABLE [dbo].[GioHangCT_Topping]  WITH CHECK ADD CHECK  (([ThanhTien]>=(0)))
GO
ALTER TABLE [dbo].[KhachHang]  WITH CHECK ADD CHECK  (([TrangThai]=(1) OR [TrangThai]=(0)))
GO
ALTER TABLE [dbo].[KhuyenMaiSanPham]  WITH CHECK ADD CHECK  (([PhanTramGiam]>=(1) AND [PhanTramGiam]<=(100)))
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD CHECK  (([TrangThai]=(1) OR [TrangThai]=(0)))
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD CHECK  (([VaiTro]=(2) OR [VaiTro]=(1)))
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD CHECK  (([TrangThai]=(1) OR [TrangThai]=(0)))
GO
ALTER TABLE [dbo].[SizeSanPham]  WITH CHECK ADD CHECK  (([TrangThai]=(1) OR [TrangThai]=(0)))
GO
ALTER TABLE [dbo].[SuKienKhuyenMai]  WITH CHECK ADD  CONSTRAINT [CK__SuKienKhu__Trang__52593CB8] CHECK  (([TrangThai]=(1) OR [TrangThai]=(0)))
GO
ALTER TABLE [dbo].[SuKienKhuyenMai] CHECK CONSTRAINT [CK__SuKienKhu__Trang__52593CB8]
GO
ALTER TABLE [dbo].[SuKienKhuyenMai]  WITH CHECK ADD  CONSTRAINT [CK_KM_Ngay] CHECK  (([NgayKT]>=[NgayBD]))
GO
ALTER TABLE [dbo].[SuKienKhuyenMai] CHECK CONSTRAINT [CK_KM_Ngay]
GO
ALTER TABLE [dbo].[Topping]  WITH CHECK ADD CHECK  (([GiaThem]>=(0)))
GO
ALTER TABLE [dbo].[Topping]  WITH CHECK ADD CHECK  (([TrangThai]=(1) OR [TrangThai]=(0)))
GO
USE [master]
GO
ALTER DATABASE [Website_BanTraSua] SET  READ_WRITE 
GO
