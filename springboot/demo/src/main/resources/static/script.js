// Hàm hiển thị tab được chọn
function showTab(tabName) {
  // Ẩn tất cả các phần nội dung
  document.getElementById("content").style.display = "none";
  document.getElementById("quan-ly-benh-nhan").style.display = "none";

  // Hiển thị phần nội dung tương ứng với tab được chọn
  if (tabName === "quan-ly-benh-nhan") {
    document.getElementById("quan-ly-benh-nhan").style.display = "block";
  } else {
    document.getElementById("content").style.display = "block";
    document.getElementById("content").innerHTML = `<h1>${tabName
      .replace(/-/g, " ")
      .toUpperCase()}</h1><p>Nội dung của tab ${tabName.replace(
      /-/g,
      " "
    )} sẽ được hiển thị ở đây.</p>`;
  }
}
// Biến lưu trữ dòng được chọn
let selectedRow = null;

// Xử lý sự kiện chọn dòng trong bảng
const rows = document.querySelectorAll("#danhSachBenhNhan tbody tr");
rows.forEach((row) => {
  row.addEventListener("click", function () {
    if (selectedRow) {
      selectedRow.classList.remove("selected");
    }
    this.classList.add("selected");
    selectedRow = this;
  });
});

// Xử lý sự kiện nút "Chỉnh sửa"
document.getElementById("btnChinhSua").addEventListener("click", function () {
  if (selectedRow) {
    // 1. Lấy dữ liệu từ dòng được chọn
    const cells = selectedRow.querySelectorAll("td");
    const id = cells[0].textContent;

    // 2. Điền dữ liệu vào form (giữ nguyên chức năng cũ)
    document.getElementById("id").value = id;
    document.getElementById("ten").value = cells[1].textContent;
    document.getElementById("gioi").value = cells[2].textContent;
    document.getElementById("dia_chi").value = cells[3].textContent;

    // Xử lý ngày sinh
    let ngaySinhText = cells[4].textContent;
    let parts = ngaySinhText.split("/");
    if (parts.length === 3) {
      let ngayISO = `${parts[2]}-${parts[1].padStart(
        2,
        "0"
      )}-${parts[0].padStart(2, "0")}`;
      document.getElementById("ngay_sinh").value = ngayISO;
    }

    document.getElementById("bhyt").value = cells[5].textContent;
    fetch(`http://localhost:8080/api/benh-nhan/${id}`, {
      method: "DELETE",
    }).then((response) => {
      if (!response.ok) {
        throw new Error("Xóa không thành công");
      }
      // 4. Xóa dòng khỏi bảng (giữ nguyên chức năng cũ)
      selectedRow.remove();
      selectedRow = null;
    });
  } else {
    alert("Vui lòng chọn một bệnh nhân để chỉnh sửa.");
  }
});

// Thêm sự kiện để điền dữ liệu vào form khi chọn dòng
document.querySelectorAll("#danhSachBenhNhan tbody tr").forEach((row) => {
  row.addEventListener("click", function () {
    if (selectedRow) {
      selectedRow.classList.remove("selected");
    }
    this.classList.add("selected");
    selectedRow = this;

    // Điền dữ liệu vào form
    const cells = this.querySelectorAll("td");
    document.getElementById("id").value = cells[0].textContent;
    document.getElementById("ten").value = cells[1].textContent;
    document.getElementById("gioi").value = cells[2].textContent;
    document.getElementById("dia_chi").value = cells[3].textContent;

    // Xử lý ngày sinh
    const ngaySinhText = cells[4].textContent;
    const parts = ngaySinhText.split("/");
    if (parts.length === 3) {
      const ngayISO = `${parts[2]}-${parts[1].padStart(
        2,
        "0"
      )}-${parts[0].padStart(2, "0")}`;
      document.getElementById("ngay_sinh").value = ngayISO;
    }

    document.getElementById("bhyt").value = cells[5].textContent;
  });
});
// Xử lý sự kiện nút "Xóa"
document.getElementById("btnXoa").addEventListener("click", function () {
  if (selectedRow) {
    const id = selectedRow.querySelector("td").textContent;

    if (confirm("Bạn có chắc chắn muốn xóa bệnh nhân này?")) {
      fetch(`http://localhost:8080/api/benh-nhan/${id}`, {
        method: "DELETE",
      })
        .then((response) => {
          if (response.ok) {
            selectedRow.remove();
            selectedRow = null;
          } else {
            alert("Xóa bệnh nhân thất bại!");
          }
        })
        .catch((error) => {
          console.error("Lỗi khi xóa bệnh nhân:", error);
          alert("Đã xảy ra lỗi khi xóa bệnh nhân!");
        });
    }
  } else {
    alert("Vui lòng chọn một bệnh nhân để xóa.");
  }
});
// Gọi API để lấy dữ liệu bệnh nhân
fetch("http://localhost:8080/api/benh-nhan")
  .then((response) => response.json())
  .then((data) => {
    console.log(data);
    const tableBody = document.querySelector("#danhSachBenhNhan tbody");
    data.forEach((benhNhan) => {
      const row = document.createElement("tr");
      row.innerHTML = `
                        <td>${benhNhan.id}</td>
                        <td>${benhNhan.ten}</td>
                        <td>${benhNhan.gioiTinh}</td>
                        <td>${benhNhan.diaChi}</td>
                        <td>${new Date(
                          benhNhan.ngaySinh
                        ).toLocaleDateString()}</td>
                        <td>${benhNhan.bhyt}</td>
                    `;
      // Gán sự kiện click cho từng dòng
      row.addEventListener("click", function () {
        if (selectedRow) {
          selectedRow.classList.remove("selected");
        }
        this.classList.add("selected");
        selectedRow = this;
      });
      tableBody.appendChild(row);
    });
  })
  .catch((error) => console.error("Lỗi khi tải dữ liệu:", error));

document.addEventListener("DOMContentLoaded", function () {
  document
    .getElementById("formThemBenhNhan")
    .addEventListener("submit", function (e) {
      // Lấy giá trị từ form
      const id = document.getElementById("id").value;
      const ten = document.getElementById("ten").value;
      const gioi = document.getElementById("gioi").value;
      const dia_chi = document.getElementById("dia_chi").value;
      const ngay_sinh = document.getElementById("ngay_sinh").value;
      const bhyt = document.getElementById("bhyt").value;

      console.log("ID:", id);
      console.log("Tên:", ten);
      console.log("Giới tính:", gioi);
      console.log("Địa chỉ:", dia_chi);
      console.log("Ngày sinh:", ngay_sinh);
      console.log("BHYT:", bhyt);
      e.preventDefault();

      // Tạo đối tượng bệnh nhân
      const benhNhan = {
        id: id,
        ten: ten,
        gioiTinh: gioi,
        diaChi: dia_chi,
        ngaySinh: ngay_sinh,
        bhyt: bhyt,
      };

      // Gửi dữ liệu lên server
      fetch("http://localhost:8080/api/benh-nhan", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(benhNhan),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Thêm bệnh nhân thành công:", data);

          // Thêm vào bảng
          const tableBody = document.querySelector("#danhSachBenhNhan tbody");
          const newRow = document.createElement("tr");
          newRow.innerHTML = `
            <td>${data.id}</td>
            <td>${data.ten}</td>
            <td>${data.gioiTinh}</td>
            <td>${data.diaChi}</td>
            <td>${new Date(data.ngaySinh).toLocaleDateString()}</td>
            <td>${data.bhyt}</td>
        `;
          tableBody.appendChild(newRow);
          // Thêm sự kiện click cho dòng mới
          newRow.addEventListener("click", function () {
            if (selectedRow) {
              selectedRow.classList.remove("selected");
            }
            this.classList.add("selected");
            selectedRow = this;
          });
          // Reset form
          document.getElementById("formThemBenhNhan").reset();
        })
        .catch((error) => console.error("Lỗi khi thêm bệnh nhân:", error));
    });
});
