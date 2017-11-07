$(document).ready(function() {

	loadItems();

	setUpPurchase();

	makePurchase();
	returnChange();

});

function loadItems(){
	clearItems();

	var contentButton = $('#itemContent');

	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/VendingMachineSpringMVC/items',
		success: function(data, status) {
			$.each(data, function(index, item) {
				var id= item.id;
				var name= item.name;
				var price= item.price;
				var quantity= item.quantity;

				var	content ='<button type="button" id="itemButton" class="btn btn-default btn-sq-lg " style="margin-bottom:50px; margin-right: 25px;" onclick="getItemButton('+ id +')">';

					content += '<tr>';
					
					content += '<p align="left" style="vertical align: top;">' + id + ' '+'</p>' ;
					
					content += '<p align= "center">' + name + '</p>';

				  	content += '<p align="center">'+ ' $' +price + '</p>';

				  	content +='<p align="center">' +' Quantity Left: '+ quantity + '</p>';

				  	content += '</tr>';

				  	content += '</button>';

				contentButton.append(content);

			});
		},
		error: function(){
			  $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service.  Please try again later.'));
		}
	});
}

function clearItems(){
	$('#itemContent').empty();
}

function getItemButton(itemId) {
	$('#errorMessages').empty();
	$('#itemId').val(itemId);
}


function setUpPurchase(){
	//add money
	
	$('#add-dollar-button').click(function(){
		addMoney(1);
	}); 

	$('#add-quarter-button').click(function() {
		addMoney(0.25);
	});

	$('#add-dime-button').click(function() {
		(addMoney(0.10));
	});

	$('#add-nickel-button').click(function() {
		(addMoney(0.05));
	});

}
function addMoney(coins){
	var money = parseFloat($('#add-money').val());
	if(isNaN(money)){
		money = 0.0;
	}
	money += coins;
	$('#add-money').val(money.toFixed(2));
}


function makePurchase(){
	$('#make-purchase-button').click(function(event){

		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/VendingMachineSpringMVC/money/' + $('#add-money').val() +'/item/' +  $('#itemId').val(),
			success: function(data){
				$('#errorMessages').empty();

				//display change
				displayChange(data);

				$('#display-messages').val('Thank you!!');
				$('#change-return-button').hide();	
				//reload page
				loadItems();
				$('#itemId').val('');
				$('#add-money').val('');

			},
			error: function(data){
				$('#errorMessages').empty();
				$('#change-return-button').show();
				$('#change-return-button').click(function(data){
					displayChange(data);
				})

				//if money vended is not enough or item is sold out= display error in message box
				if(data.status == 422) {
					var errorMessage = JSON.parse(data.responseText);
					$('#display-messages')
						.val(errorMessage.message);
				} else { 
					$('#errorMessages')
               			.append($('<li>')
               			.attr({class: 'list-group-item list-group-item-danger'})
               			.text('Error calling web service.  Please try again later.'));
				}
			}			
		});
	});
}

function displayChange(data){
	if(data.quarters == 0 && data.dimes == 0 && data.nickels == 0 && data.pennies == 0){
		$('#display-change').val('No Change');
	} else{
		$('#display-change').val(' Quarter(s): ' + data.quarters + ' Dime(s):' + data.dimes 
		+ ' Nickel(s):' + data.nickels + ' Pennies:' + data.pennies);
	} 
}


function maunalChange(){
	var money = parseFloat($('#add-money').val());
	if(isNaN(money)){
		money = 0.0;
	}
	money = (100 * money).toPrecision(3);
	//change
	var numberOfQuarters = Math.floor(money / 25).toPrecision(1);
	if(numberOfQuarters > 0){
		money = money % 25;
	}

	var numberOfDimes = Math.floor(money / 10).toPrecision(1);
	if(numberOfDimes > 0){
		money = money % 10;
	}
		
	var numberOfNickels = Math.floor(money / 5).toPrecision(1);
	if(numberOfNickels > 0){
		money = money % 5;
	}
	var numberOfPennies = money.toPrecision(1);


	$('#display-change').val("Quarters:"+ numberOfQuarters + " Dimes:"+ numberOfDimes 
		+ " Nickels:" + numberOfNickels + " Pennies:" + numberOfPennies);
	
}

function returnChange(){
	$('#change-return-button').click(function(){
		maunalChange();
		$('#display-messages').val(' ');
		$('#itemId').val(' ');

		$('#add-money').val('');
		
	});
}









