/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function updateDishesGet(id) {
    $.ajax({
        type: "get",
        url: "/J3LP0067/UpdateController",
        data: {
            id: id
        }, // serializes the form's elements.
        success: function (data)
        {
            var formUpdate = document.getElementById("overlay");
            formUpdate.innerHTML = data;
        }
    });
    document.getElementById("overlay").style.display = "block";
}
function updateDishes() {
    const id = document.getElementById("idAdd").value;
    const file = document.getElementById("files-add").value.split("\\");
    const name = document.getElementById("nameAdd").value;
    const percent = document.getElementById("percentAdd").value;
    const timeStart = document.getElementById("time-start").value;
    const timeEnd = document.getElementById("time-end").value;
    const note = document.getElementById("note-add").value;
    let image = "img/" + file[file.length - 1];
    let forwat = true;
    if (file === null || file.length === 1) {
        document.getElementById("errorImg-add").innerHTML = "chưa có ảnh";
        forwat = false;
    }
    if (name.length <= 0 || name.length > 50) {
        document.getElementById("errorNameAdd").innerHTML = "Tên khuyến mãi phải lớn 0 nhỏ hơn 50 ký tự";
        forwat = false;
    }
    if (percent <= 0 || percent >= 100) {
        document.getElementById("errorPercentAdd").innerHTML = "Phần trăm khuyến mại trong khoảng 0 đến 100 phần trăm";
        forwat = false;
    }
    //check date input
    let date1 = new Date(timeStart);
    let date2 = new Date(timeEnd);
    if (date1 > date2) {
        console.log("date 1 > date 2");
        document.getElementById("errorDate").innerHTML = "Ngày bắt đầu phải trước ngày kết thúc";
        forwat = false;
    }
    if (note.length > 200) {
        document.getElementById("errorNote-add").innerHTML = "Mô tả không được quá 200 ký tự";
        forwat = false;
    }
    if (forwat) {
        $.ajax({
            type: "post",
            url: "/J3LP0065/UpdateController",
            data: {
                id: id,
                image: image,
                name: name,
                percent: percent,
                timeStart: timeStart,
                timeEnd: timeEnd,
                note: note
            }, // serializes the form's elements.
            success: function (data)
            {
                var formUpdate = document.getElementById("runState");
                formUpdate.innerHTML = data;
            }
        });
        document.getElementById("runState").style.visibility = "visible";
        document.getElementById("saveButton").style.display = "none";
    }
}
//add------------------------------------
function addPromotionGet() {
    $.ajax({
        type: "get",
        url: "/J3LP0067/AddController",
        data: {

        }, // serializes the form's elements.
        success: function (data)
        {
            var formUpdate = document.getElementById("overlay");
            formUpdate.innerHTML = data;
        }
    });
    document.getElementById("overlay").style.display = "block";
}
function addPromotion() {
    const file = document.getElementById("files-add").value.split("\\");

    var fd = new FormData($("#formId").get(0));
    HTMLFormElement
    var files = $('#files-add')[0].files[0];
    fd.append('file', files);

    const name = document.getElementById("nameDishes-add").value;
    const typeId = document.getElementById("typeDishes-add").value;
    const statusId = document.getElementById("statusDishes-add").value;
    const price = document.getElementById("priceDishes-add").value;
    const note = document.getElementById("note-add").value;
    let image = "img/" + file[file.length - 1];
    let forwat = true;

    if (name.length <= 0 || name.length > 50) {
        document.getElementById("errorName-add").innerHTML = "Tên khuyến mãi phải lớn 0 nhỏ hơn 50 ký tự";
        forwat = false;
    }
    if (price < 1000 || price > 10000000) {
        document.getElementById("errorPrice-add").innerHTML = "giá món ăn từ 1000 đến 10000000";
        forwat = false;
    }
    if (note.length > 200) {
        document.getElementById("errorNote-add").innerHTML = "note không được quá 200 ký tự";
        forwat = false;
    }
    console.log(image);
    if (file === null || file.length === 1) {
        document.getElementById("errorImg-add").innerHTML = "chưa có ảnh";
        forwat = false;
    }
    if (forwat) {
        $.ajax({
            type: "post",
            url: "/J3LP0067/AddController",
            data: {
                image: image,
                name: name,
                typeId: typeId,
                statusId: statusId,
                price: price,
                note: note
            }, // serializes the form's elements.
            success: function (data)
            {
                var formUpdate = document.getElementById("runState");
                formUpdate.innerHTML = data;
                if (data.includes("Success")) {
                    clearData();
                }
            }
        });
        document.getElementById("runState").style.visibility = "visible";
    }
}
function clearData() {
    document.getElementById("nameDishes-add").value = "";
    document.getElementById("typeDishes-add").value = "";
    document.getElementById("statusDishes-add").value = "";
    document.getElementById("priceDishes-add").value = "";
    document.getElementById("note-add").value = "";
    document.getElementById("serviceImg").src = "";
}
function SubForm() {
    var files = $('#files-add')[0].files[0];
    var form_data = new FormData();
    form_data.append('file', files);
//    form_data.append('file', files);
    console.log(files);
    $.ajax({
        url: '/J3LP0067/AddController',
        type: 'post',
        data: form_data,
        processData: false,
        contentType: false,
        success: function () {
            alert("worked");
        }
    });
}