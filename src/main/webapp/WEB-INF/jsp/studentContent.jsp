<%@ include file="../include/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <table border="1" align="center" style="width: 100%">
        <tr>
            <td>id</td>
            <td>姓名</td>
            <td>QQ</td>
            <td>修真类型</td>
            <td>入学时间</td>
            <td>毕业院校</td>
            <td>学号</td>
            <td>日报连接</td>
            <td>立愿</td>
            <td>师兄</td>
            <td>了解渠道</td>
        </tr>
        <c:forEach items="${stuList}" var="stu">
            <tr>
                <td>${stu.id}</td>
                <td>${stu.name}</td>
                <td>${stu.qq}</td>
                <td>${stu.type}</td>
                <td>${stu.timeOf}</td>
                <td>${stu.gradeSchool}</td>
                <td>${stu.onlineId}</td>
                <td>${stu.link}</td>
                <td>${stu.wish}</td>
                <td>${stu.fellow}</td>
                <td>${stu.understand}</td>
                <td>
                    <form action="/Tiles/u/student/${stu.id}" method="post">
                        <input type="hidden" name="_method" value="delete">
                        <input type="submit" value="删除">
                    </form>
                </td>
                <td>
                    <form action="/Tiles/u/student/update/${stu.id}" method="post">
                        <input type="submit" value="更新">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="/Tiles/u/student/add">添加</a><br>
</div>