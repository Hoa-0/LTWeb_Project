// Mảng màu cố định cho các danh mục (Thêm màu nếu có nhiều hơn 8 danh mục)
const FIXED_CATEGORY_COLORS = [
    '#667eea', // Primary Blue/Purple
    '#ff6384', // Pink
    '#36a2eb', // Light Blue
    '#ff9f40', // Orange
    '#4bc0c0', // Teal
    '#9966ff', // Purple
    '#ffcd56', // Yellow
    '#c9cbcf'  // Gray
];

// 1. Định nghĩa hàm vẽ biểu đồ Doanh thu theo thời gian
window.drawRevenueChart = function (labels, values) {
    if (!labels || labels.length === 0) return;
    
    const revenueCtx = document.getElementById('revenueOverTimeChart');
    if (!revenueCtx) return;

    new Chart(revenueCtx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu (VNĐ)',
                data: values,
                fill: true, 
                borderColor: 'rgb(75, 192, 192)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)', 
                tension: 0.1 
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value, index, values) {
                            if (typeof value === 'number') {
                                return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND', minimumFractionDigits: 0, maximumFractionDigits: 0 }).format(value);
                            }
                            return value;
                        }
                    }
                }
            },
            plugins: {
                tooltip: {
                     callbacks: {
                        label: function(context) {
                            let label = context.dataset.label || '';
                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed.y !== null) {
                                 label += new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND', minimumFractionDigits: 0, maximumFractionDigits: 0 }).format(context.parsed.y);
                            }
                            return label;
                        }
                    }
                }
            }
        }
    });
};

// 2. Định nghĩa hàm vẽ biểu đồ Doanh thu theo Danh mục
window.drawCategoryChart = function (labels, values) {
    if (!labels || labels.length === 0) return;
    
    const categoryCtx = document.getElementById('revenueByCategoryChart');
    if (!categoryCtx) return;

    // Lặp qua mảng màu cố định, tự động lặp lại nếu số lượng danh mục > số màu
    const backgroundColors = labels.map((_, index) => {
        return FIXED_CATEGORY_COLORS[index % FIXED_CATEGORY_COLORS.length];
    });

    new Chart(categoryCtx, {
        type: 'doughnut', 
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu (VNĐ)',
                data: values,
                backgroundColor: backgroundColors, // SỬ DỤNG MÀU CỐ ĐỊNH
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                 legend: {
                     position: 'top', 
                 },
                 tooltip: {
                     callbacks: {
                        label: function(context) {
                            let label = context.label || '';
                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed !== null) {
                                 if (typeof context.parsed === 'number') {
                                     label += new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND', minimumFractionDigits: 0, maximumFractionDigits: 0 }).format(context.parsed);
                                 } else {
                                     label += context.parsed;
                                 }
                            }
                            return label;
                        }
                    }
                }
            }
        }
    });
};