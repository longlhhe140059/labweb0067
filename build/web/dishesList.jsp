<%-- 
    Document   : Order
    Created on : Nov 30, 2021, 2:49:20 PM
    Author     : stick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
                integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
        <script src="jquery-3.5.1.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <title>Online feast booking</title>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <link href="css/detail.css" rel="stylesheet" type="text/css"/>
        <script src="js/showDetail.js" type="text/javascript"></script>
        <script src="js/script.js" type="text/javascript"></script>
        <script src="js/updateScript.js" type="text/javascript"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="content">
            <div class="row justify-content-md-center contentnav">
                <a href="#">Thông tin</a>
                <a href="#">Ảnh</a>
                <a href="#" class="active">Thực đơn</a>
                <a href="#">Combo món ăn</a>
                <a href="#">Dịch vụ</a>
                <a href="#">Khuyến mãi</a>
                <a href="#">Đơn hàng</a>
            </div>

            <div class="row justify-content-md-center">
                <p style="margin: 0px;font-size: 20px;font-weight: 500;">Thực đơn</p>
            </div>
            <hr class="my-4 w-50">

            <div class="input-group searchbar w-75">
                <div class="form-inline">
                    <input type="search" id="searchName" class="form-control" placeholder="nhập tên món ăn" 
                           value="${searchName}" onfocus="remove_errorSearch('errorSearch')"/>

                    <select id="typeDishes" class="form-control input-field option-field" type="text" >
                        <option value="-1">Tất cả</option>
                        <c:forEach items="${tList}" var="t">
                            <option value="${t.getId()}">${t.getName()}</option>
                        </c:forEach>
                    </select>
                    <button type="button" class="btn btn-primary searchButton" onclick="">
                        <i class="bi bi-search"></i>
                    </button>
                    <button type="button" class="btn btn-primary searchButton" onclick="addPromotionGet()">
                        <i class="bi bi-plus-square"></i> Thêm Món ăn
                    </button>
                </div>
            </div>

            <div class="row justify-content-md-center orderList">
                <table>
                    <tr>
                        <th style="width: 35px;">#</th>
                        <th style="width: 500px;">Tên món ăn</th>
                        <th class="text-right" style="width: 200px;">Giá (VND)</th>
                        <th style="width: 250px;">Loại món ăn</th>
                        <th style="width: 200px;">Trạng thái</th>
                        <th style="width: 150px;"></th>
                    </tr>
                    <c:forEach items="${dList}" var="d">
                        <tr>
                            <td>${d.getId()}</td>
                            <td>${d.getName()}</td>
                            <td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${d.getPrice()}"/> VNĐ</td>
                            <td>${d.getType().getName()}</td>
                            <td>${d.getStatus().getName()}</td>
                            <td>
                                <button type="button" class="btn btn-primary" onclick="updateDishesGet(${d.getId()})">
                                    <i class="bi bi-pencil-square"></i> Sửa
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="paging">
                <c:if test="${curPage > 1}">
                    <button type="button" class="btn btn-outline-success" onclick="onClickPageIndex(${curPage-1})">Trang trước</button>
                </c:if>

                <c:forEach step="1" begin="1" end="${totalPage}" var="index">
                    <c:if test="${curPage eq index}">
                        <button type="button" class="btn btn-success" onclick="onClickPageIndex(${index})">${index}</button>
                    </c:if>
                    <c:if test="${curPage ne index}">
                        <button type="button" class="btn btn-outline-success" onclick="onClickPageIndex(${index})">${index}</button>
                    </c:if>
                </c:forEach>

                <c:if test="${curPage < totalPage}">
                    <button type="button" class="btn btn-outline-success" onclick="onClickPageIndex(${curPage+1})">Trang sau</button>
                </c:if>
            </div>
        </div>

        <div id="overlay" onclick="">
            <div class="w-25 detailOrder overflow-auto">
                <div class="detailHeader">
                    <div class="header_left">
                        <h6>Thêm Món ăn</h6>
                        <div id="runState" style="visibility: hidden;display: inline;">server controller error</div>
                    </div>
                    <div class="header_right">
                        <button type="button" onclick="offDetail1()"><i class="bi bi-x"></i></button>
                    </div>
                    <hr class="my-3">
                </div>

                <div class="detailBody">
                    <div class="choose-image-update">
                        <div class="component-image-update">
                            <input id="files-add" name="imageService" class="choose-file-update" type="file" onchange="chooseImageModalUpdate('files-add')"/>
                            <label for="files-add">
                                <img src="" width="370" alt="" id="serviceImg" style="" class="image-add-update"/>
                                Chọn hoặc đổi ảnh
                            </label>
                        </div>
                    </div>
                    <div class="ml-1 mr-1 error" id="errorImg-add"></div>

                    <div class="row ml-1 mr-1 mb-1">
                        <label for="nameDishes-add">
                            <strong>Tên khuyến mãi</strong>
                        </label>
                        <input id="nameDishes-add" class="form-control" type="text" placeholder="Nhập tên món ăn" onfocus="remove_errorSearch('errorName-add')"/>
                    </div>
                    <div class="ml-1 mr-1 error" id="errorName-add"></div>

                    <div class="row ml-1 mr-1 mb-1">
                        <label for="typeDishes-add">
                            <strong>Loại hình</strong>
                        </label>
                        <select id="typeDishes-add" class="form-control input-field" type="text" >
                            <option value="">option 1</option>
                            <option value="">option 2</option>
                        </select>
                    </div>

                    <div class="row ml-1 mr-1 mb-1">
                        <label for="statusDishes-add">
                            <strong>Trạng thái</strong>
                        </label>
                        <select id="statusDishes-add" class="form-control input-field" type="text" >
                            <option value="">option 1</option>
                            <option value="">option 2</option>
                        </select>
                    </div>

                    <div class="row ml-1 mr-1 mb-1">
                        <label for="priceDishes-add">
                            <strong>Tên khuyến mãi</strong>
                        </label>
                        <input id="priceDishes-add" class="form-control" type="number" placeholder="Nhập giá món ăn" onfocus="remove_errorSearch('errorPrice-add')"/>
                    </div>
                    <div class="ml-1 mr-1 error" id="errorPrice-add"></div>

                    <div class="row ml-1 mr-1 mb-1">
                        <label for="note-add">
                            <strong>Mô tả</strong>
                        </label>
                        <textarea class="form-control" id="note-add" rows="3" placeholder="Mô tả dịch vụ"onfocus="remove_errorSearch('errorNote-add')"></textarea>
                    </div>
                    <div class="ml-1 mr-1 error" id="errorNote-add"></div>
                    <button id="buttonSave" type="button" class="btn btn-success ml-1" style="width: 100%;margin-top: 10px;" onclick="">Lưu</button>
                </div>
                <div class="detailFooter">
                    <hr class="my-3">
                    <button type="button" class="btn btn-secondary ml-1" onclick="offDetail()">Quay lại</button>
                </div>
            </div>
        </div>

        <jsp:include page="footer.jsp"/>
    </body>
</html>
