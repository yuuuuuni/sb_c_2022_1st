<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 리스트" />
<%@ include file="../common/head.jspf"%>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <div class="table-box-type-1">
      <table>
        <colgroup> <!-- 테이블 각 컬럼의 너비를 정해주는 속성, table 태그 밑에다 씀 -->
          <col width="50" /> <!-- 번호 -->
          <col width="150" /> <!-- 작성날짜 -->
          <col width="150" /> <!-- 수정날짜 -->
          <col width="150" /> <!-- 작성자 -->
          <col /> <!-- 제목은 너비 조정 필요 없으므로 그냥 놔둬도 됨 -->
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>작성날짜</th>
            <th>수정날짜</th>
            <th>작성자</th>
            <th>제목</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="article" items="${articles}">
            <tr>
              <td>${article.id}</td>
              <td>${article.regDate.substring(2, 16)}</td>
              <td>${article.updateDate.substring(2, 16)}</td>
              <td>${article.memberId}</td>
              <td>
                <a class="hover:underline" href="../article/detail?id=${article.id}">${article.title}</a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf"%>