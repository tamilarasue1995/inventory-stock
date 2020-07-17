'use strict';
function bindEvents(){
	this.stockNumber = document.getElementById('stock-number');
	this.stockName = document.getElementById('stock-name');
	this.purchaseDate = document.getElementById('stock-purchase-date');
	this.purchasePrice = document.getElementById('stock-purchase-price');
	this.sellingDate = document.getElementById('stock-selling-date');
	this.sellingPrice = document.getElementById('stock-selling-price');
	this.sellingDateDiv = document.getElementById('stock-selling-date-div');
	this.sellingPriceDiv = document.getElementById('stock-selling-price-div');
	this.quantity = document.getElementById('stock-quantity');
	this.createBtn = document.getElementById('create-button');
	this.backBtn = document.getElementById('back-button');
	if(document.getElementById('edit-mode').value === "false"){
		this.sellingDateDiv.setAttribute('style','display:none');
		this.sellingPriceDiv.setAttribute('style','display:none');
	} else {
		loadEditMode(JSON.parse(document.getElementById('data-edit').value),this);
	}
	var self = this;
	this.createBtn.addEventListener('click', addStock.bind(self), false);
	this.backBtn.addEventListener('click', backToPortal, false);
};

function addStock(){
	console.log('add method');
	var data = {
			'stockNumber':this.stockNumber.value,
			'stockName':this.stockName.value,
			'purchaseDate':this.purchaseDate.value,
			'purchasePrice':this.purchasePrice.value,
			'sellingDate': this.sellingDate.value,
			'sellingPrice': this.sellingPrice.value,
			'quantity':this.quantity.value
	}
	var canSend=true;
	var formElements = document.querySelectorAll('.form-control');
	for(let i=1;i<formElements.length;i++){
		if(!formElements[i].checkValidity()) {
			canSend = false;
		}
	}
	if(canSend){
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", document.location.origin + "/stock/inventory/create", true);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				$('.toast')[0].innerText="Stock Created Success Fully";
				$('.toast').toast({
					delay:2000
				});
				$('.toast').css({'background':'#007bff','color':'white'});
				$('.toast').toast('show');
				window.location.href = 'portal';
			}
		};
		xhttp.send(JSON.stringify(data));
	} else {
		$('.toast')[0].innerText="Enter Mandatory Fields";
		$('.toast').css({'background':'#dc3545','color':'white'});
		$('.toast').toast({
			delay:2000
		});
		$('.toast').toast('show');
	}
};


$(document).ready(function(){
	$("#stock-number").focusout(function(){
		if($(this).val() !== '')
		checkStockNumber($(this).val());
	});	
});

function checkStockNumber(data){
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", document.location.origin + "/stock/inventory/checkStock", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200 && this.responseText === "true") {
			document.getElementById('stock-number').focus();
			document.getElementById('stock-number').value = '';
			$('.toast')[0].innerText="Stock Number Already Exist";
			$('.toast').toast({
				delay:2000
			});
			$('.toast').css({'background':'#dc3545','color':'white'});
			$('.toast').toast('show');
		}
	};
	xhttp.send(JSON.stringify({ 'columnName' : data}));
}

function backToPortal() {
	window.location.href = 'portal';
	console.log('back method');
};

function loadEditMode(data,self){
	self.stockNumber.value = data.stockNumber;
	self.stockName.value = data.stockName;
	self.purchaseDate.value = data.purchaseDate === null ? null : getDateValue(data.purchaseDate);
	self.purchasePrice.value = data.purchasePrice;
	self.sellingDate.value = data.sellingDate === null ? null : getDateValue(data.sellingDate);
	self.sellingPrice.value = data.sellingPrice === 0 ? "" : data.sellingPrice;
	self.quantity.value = data.quantity;
	self.stockNumber.setAttribute('disabled','true');
	self.createBtn.innerText='Update';
	self.stockNumber.parentNode.firstElementChild.firstElementChild.innerText = "Stock Number";
};

function getDateValue(data){
	if(data.monthValue < 10){
		return data.year + '-0' + data.monthValue + '-'+data.dayOfMonth;
	} else {
		return data.year + '-' + data.monthValue + '-'+data.dayOfMonth;
	}
};

document.addEventListener('DOMContentLoaded', bindEvents, false);
