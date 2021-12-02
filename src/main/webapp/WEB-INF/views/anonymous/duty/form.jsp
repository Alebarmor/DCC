<%--
- form.jsp
-
- Copyright (C) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="anonymous.duty.form.label.title" path="title"/>
	<acme:form-moment code="anonymous.duty.form.label.startTime" path="startTime"/>
	<acme:form-moment code="anonymous.duty.form.label.endTime" path="endTime"/>
	<acme:form-textbox code="anonymous.duty.form.label.workload" path="workload"/>
	<acme:form-textarea code="anonymous.duty.form.label.description" path="description"/>
	<acme:form-url code="anonymous.duty.form.label.info" path="info"/>
	
	<acme:form-return code="anonymous.duty.form.button.return"/>
</acme:form>
