<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
	<tbody>
		<tr>
			<th><span>今日来访：</span></th>
			<td><span>${accessMap.todayaccess}次</span></td>
		</tr>
		<tr>
			<th><span>本月来访：</span></th>
			<td><span>${accessMap.monthaccess}次</span></td>

		</tr>
		<tr>
			<th><span>累计访问：</span></th>
			<td><span>${accessMap.sumaccess}次</span></td>
		</tr>
		<tr>
			<th><span>今日消费访问点：</span></th>
			<td><span>${accessMap.todaypoint}</span></td>
		</tr>
		<tr>
			<th><span>本月消费访问点：</span></th>
			<td><span>${accessMap.monthpoint}</span></td>

		</tr>
		<tr>
			<th><span>累计消费访问点：</span></th>
			<td><span>${accessMap.sumpoint}</span></td>
		</tr>
	</tbody>
</table>