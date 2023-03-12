/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function onClickPageIndex(Pageindex) {
    
    window.location.href = "ListController?page=" + Pageindex;
}
function searchService() {
    let searchName = document.getElementById("searchName").value.trim();
    let searchType = document.getElementById("typeDishes").value;
    let forwat = true;
    if (searchName.length <= 0 || searchName.length >= 50) {
        document.getElementById('errorSearch').innerHTML = "tên món ăn lớn hơn 0 ký tự nhỏ hơn 50 ký tự";
        forwat = false;
    }

    if (forwat) {
        window.location.href = "ListController?searchName=" + searchName + "&typeID=" + searchType + "&page=" + 1;
    }
}
function remove_errorSearch(id) {
    document.getElementById(id).innerHTML = "";
}
function chooseImageModalUpdate(tagId) {
    const file = document.getElementById(tagId).value.split("\\");
    console.log(file);
    document.getElementById("serviceImg").src = "img/" + file[file.length - 1];
    
    document.getElementById("errorImg-add").innerHTML="";
}




