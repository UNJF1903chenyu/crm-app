$(document).ready(function () {
	var updateId;
	$("button").click(function () {
		var name = $(".name").val();
		var price = $(".price").val();
		$.ajax({
			type : "post",
			url : "http://localhost:8080/crm/add_product",
			dataType : 'json',
			data : {
				"name" : name,
				"price" : parseFloat(price)
			},
			success : function(data) {
				window.location.reload(); 

			}
		});
	})
	$(".delete").on("click",function () {
		var id = $(this).parent().parent().find(".id").text();
		$.ajax({
			type : "post",
			url : "http://localhost:8080/crm/delete_product",
			dataType : 'json',
			data : {
				"id" : id
			},
			success : function(data) {
				window.location.reload(); 
			}
		});
	})
		$(".update").on("click",function () {
			updateId = $(this).parent().parent().find(".id").text();
			$(".windos").css("display","block");
		
	})
		$("#ok").on("click",function(){
			var updateName = $(".after-name").val();
			var updatePrice = $(".after-price").val();
			$.ajax({
				type : "post",
				url : "http://localhost:8080/crm/update_product",
				dataType : 'json',
				data : {
					"id":parseFloat(updateId),
					"name" : updateName,
					"price" : parseFloat(updatePrice)
				},
				success : function(data) {
					window.location.reload(); 

				}
			});
			$(".windos").css("display","none");
		})
		$("#previous").on("click",function(){
			setPage(-1)
		})
		$("#next").on("click",function(){
			setPage(1)
		})
		

		function setPage(offset){
			$.ajax({
				type : "post",
				url : "http://localhost:8080/crm/setpage",
				dataType: 'json',
				data:{"pageOffset":offset},
				success : function(data) {
					window.location.reload()
				}
			});
		}

	$(".page-link").on("click",function(){
		$.ajax({
			type : "post",
			url : "http://localhost:8080/crm/setactualpage",
			dataType: 'json',
			data:{"pages": parseInt($(this).text())},
			success : function(data) {
				window.location.reload()
			}
		});
	})
		
		
		
		
		
})