$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == ""){
		$("#alertSuccess").hide();
	}
	
	$("#alertError").hide();
});
	
//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
		
	// Form validation-------------------
	var status = validateMonitoringForm();
	if (status != true){
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
		
	// If valid------------------------
	var type = ($("#hidConIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
	{
		url : "overallMonitoringAPI",
		type : type,
		data : $("#formCon").serialize(),
		dataType : "text",
		complete : function(response, status){
			onMonitoringSaveComplete(response.responseText, status);
		}
		});
});



function onMonitoringSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		} else if (status == "error")
		{
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		} else
		{
			$("#alertError").text("Unknown error while saving..");
			$("#alertError").show();
	}
		$("#hidConIDSave").val("");
		$("#formCon")[0].reset();
}



//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidConIDSave").val($(this).data("conceptcode"));
	$("#pay_ID").val($(this).closest("tr").find('td:eq(1)').text());
	$("#power_consumption_ID").val($(this).closest("tr").find('td:eq(2)').text());
	$("#month").val($(this).closest("tr").find('td:eq(3)').text());
	$("#comment").val($(this).closest("tr").find('td:eq(4)').text());
});


//DELETE=================================================
$(document).on("click", ".btnRemove", function(event)
		{
			$.ajax(
			{
				url : "overallMonitoringAPI",
				type : "DELETE",
				data : "monitoring_ID=" + $(this).data("monitoring_ID"),
				dataType : "text",
				complete : function(response, status)
				{
					onItemDeleteComplete(response.responseText, status);
				}
			});
});


function onItemDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		} else if (status == "error")
		{
			$("#alertError").text("Error while deleting.");
			$("#alertError").show();
		} else
		{
			$("#alertError").text("Unknown error while deleting..");
			$("#alertError").show();
	}
}


//========== VALIDATION ================================================
function validateMonitoringForm()
{
		/*// Month
		if ($("#month").val().trim() == "")
		{
			return "Insert Month!!";
		}
		
		// Previous Month Reading
		if ($("#premonreading").val().trim() == "")
		{
			return "Insert Previous Month Reading!!";
		}
		
		// Current Month Reading
		if ($("#curmonreading").val().trim() == "")
		{
			return "Insert Current Month Reading!!";
		}
		
		// is numerical value
		var premonreading = $("#premonreading").val().trim();
		if (!$.isNumeric(premonreading))
		{
			return "Invalid Previous Month Reading (Please enter a number)";
		}
		
		var curmonreading = $("#curmonreading").val().trim();
		if (!$.isNumeric(curmonreading))
		{
			return "Invalid Current Month Reading (Please enter a number)";
		}*/
		
		return true;
}
