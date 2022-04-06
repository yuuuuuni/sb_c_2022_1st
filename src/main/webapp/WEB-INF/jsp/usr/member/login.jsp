<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="로그인" />
<%@ include file="../common/head.jspf"%>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doLogin">
      <table>
        <colgroup>
          <col width="200" /> <!-- 컬럼(첫번째 열) -->
          <col /> <!-- 데이터(두번째 열) 너비 줄 필요 없으면 이렇게 그냥 빈칸으로 놔둬도 됨 -->
        </colgroup>
        <tbody>
          <tr>
            <th>로그인아이디</th>
            <td>
              <input class="w-96 input input-bordered" name="loginId" type="text" placeholder="로그인아이디" />
            </td>
          </tr>
          <tr>
            <th>로그인비밀번호</th>
            <td>
              <input class="w-96 input input-bordered" name="loginPw" type="password" placeholder="로그인비밀번호" />
            </td>
          </tr>
          <tr>
            <th>로그인</th>
            <td>
              <button type="submit" class="btn btn-primary">로그인</button>
              <button type="button" class="btn btn-outline btn-secondary" onclick="history.back();">뒤로가기</button>    
            </td>
          </tr>
        </tbody>
      </table>
    </form>
  </div>
</section>

<%@ include file="../common/foot.jspf"%>