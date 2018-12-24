<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


<script>
var deleteOrder = false;
var deleteOrderid = 0;

$(function(){
	$("a[orderStatus]").click(function(){
		var orderStatus = $(this).attr("orderStatus");
		if('all'==orderStatus){
			$("table[orderStatus]").show();	
		}
		else{
			$("table[orderStatus]").hide();
			$("table[orderStatus="+orderStatus+"]").show();			
		}
		
		$("div.orderType div").removeClass("selectedOrderType");
		$(this).parent("div").addClass("selectedOrderType");
	});
	
	$("a.deleteOrderLink").click(function(){
		deleteOrderid = $(this).attr("oid");
		deleteOrder = false;
		$("#deleteConfirmModal").modal("show");
	});
	
	$("button.deleteConfirmButton").click(function(){
		deleteOrder = true;
		$("#deleteConfirmModal").modal('hide');
	});	
	
	$('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
		if(deleteOrder){
			var page="foredeleteOrder";
			$.post(
				    page,
				    {"oid":deleteOrderid},
				    function(result){
				        console.log(typeof "0");
				        console.log(typeof result);
						if("success" != "0"){
							$("table.orderListItemTable[oid="+deleteOrderid+"]").hide();
						}
						else{
							location.href="login.jsp";
						}
				    }
				);
			
		}
	})		
	
	$(".ask2delivery").click(function(){
		var link = $(this).attr("link");
		$(this).hide();
		page = link;
		$.ajax({
			   url: page,
			   success: function(result){
				alert("ojbk")
			   }
			});
		
	});
});

</script>
	
<div class="boughtDiv">
	<div class="orderType">
		<div class="selectedOrderType"><a orderStatus="all" href="/forebought">所有订单</a></div>
		<div><a  orderStatus="waitPay" href="${pageContext.request.contextPath}/forebought?op=topay">待付款</a></div>
		<div><a  orderStatus="waitReview" href="${pageContext.request.contextPath}/forebought?op=to_com" class="noRightborder">待评价</a></div>
		<div class="orderTypeLastOne"><a class="noRightborder">&nbsp;</a></div>
	</div>
	<div style="clear:both"></div>
	<div class="orderListTitle">
		<table class="orderListTitleTable">
			<tr>
				<td>房间</td>
				<td width="100px">单价</td>
				<td width="100px">数量</td>
				<td width="120px">实付款</td>
				<td width="100px">交易操作</td>
			</tr>
		</table>
	
	</div>
	
	<div class="orderListItem">
		<c:forEach items="${os}" var="o">
			<table class="orderListItemTable" orderStatus="${o.status}" oid="${o.id}">
				<tr class="orderListItemFirstTR">
					<td colspan="2">
					<b><fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></b> 
					<span>订单号: ${o.orderCode} 
					</span>
					</td>
					<td  colspan="2"><img width="13px" src="img/site/promise.png">GoTrip</td>

					<td class="orderItemDeleteTD">
						<a class="deleteOrderLink" oid="${o.id}" href="deleteorder?oid=${o.id}">
							<span  class="orderListItemDelete glyphicon glyphicon-trash"></span>
						</a>
					</td>
				</tr>
				<c:forEach items="${o.orderItems}" var="oi" varStatus="st">
					<tr class="orderItemProductInfoPartTR" >
						<td class="orderItemProductInfoPartTD"><img width="80" height="80" src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg"></td>
						<td class="orderItemProductInfoPartTD">
							<div class="orderListItemProductLinkOutDiv">
								<a href="foreproduct?pid=${oi.product.id}">${oi.product.name}</a>
								<div class="orderListItemProductLinkInnerDiv">
											<img src="img/site/creditcard.png" title="支持信用卡支付">
											<img src="img/site/7day.png" title="消费者保障服务,承诺7天退货">
											<img src="img/site/promise.png" title="消费者保障服务,承诺如实描述">						
								</div>
							</div>
						</td>
						<td  class="orderItemProductInfoPartTD" width="100px">
						
							<div class="orderListItemProductOriginalPrice">￥<fmt:formatNumber type="number" value="${oi.product.orignalPrice}" minFractionDigits="2"/></div>
							<div class="orderListItemProductPrice">￥<fmt:formatNumber type="number" value="${oi.product.promotePrice}" minFractionDigits="2"/></div>
						</td>

						<c:if test="${st.count==1}">
						 
							<td valign="top" rowspan="${fn:length(o.orderItems)}" class="orderListItemNumberTD orderItemOrderInfoPartTD" width="100px">
								<span class="orderListItemNumber">${o.totalNumber}</span>
							</td>
							<td valign="top" rowspan="${fn:length(o.orderItems)}" width="120px" class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD">
								<div class="orderListItemProductRealPrice">￥<fmt:formatNumber  minFractionDigits="2"  maxFractionDigits="2" type="number" value="${o.total}"/></div>

							</td>
							<td valign="top" rowspan="${fn:length(o.orderItems)}" class="orderListItemButtonTD orderItemOrderInfoPartTD" width="100px">
								<c:if test="${o.status=='waitConfirm' }">
									<a href="foreconfirmPay?oid=${o.id}">
										<button class="orderListItemConfirm">确认入住</button>
									</a>
								</c:if>
								<c:if test="${o.status=='waitPay' }">
									<a href="forealipay?oid=${o.id}&total=${o.total}">
										<button class="orderListItemConfirm">付款</button>
									</a>								
								</c:if>
								


								<c:if test="${o.status=='waitReview' }">
									<a href="forereview?oid=${o.id}">
										<button  class="orderListItemReview">评价</button>
									</a>
								</c:if>
							</td>						
						</c:if>
					</tr>
				</c:forEach>		
				
			</table>
		</c:forEach>
		
	</div>
	
</div>