'use strict';

function loadPortal(){
	this.sortOrder = 'asc';
	this.sortComponent = 'stock_number';
	this.createButton = document.getElementById('create-button');
	this.createButton.addEventListener('click',function(){
		window.location.href = 'manage';
	}, false);
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", document.location.origin + "/", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			JSON.parse(this.responseText).forEach(function(element,index){
				createInboxRow(element);
			});
		}
	};
	xhttp.send();
};

function editMode(self){
	var stockNumber = self.parentElement.firstChild.innerText;
	window.location.href = 'manage?stockNumber='+stockNumber;
};

function sortRows(self){
	if(!self.sortOrder){
		self.sortOrder='';
	}
	if(!self.sortComponent){
		self.sortComponent = '';
	}
	var newOrder;
	var newSortComponent;
	var isAsc;
	if(self.sortOrder === 'asc' && self.sortComponent !== self.parentElement.id){
		newOrder='desc';
		isAsc = false;
	} else {
		newOrder='asc';
		isAsc=true;
	}
	newSortComponent=self.parentElement.id;
	self.sortOrder= newOrder;
	self.sortComponent === self.parentElement.id
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", document.location.origin + "/stock/inventory/sort", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			JSON.parse(this.responseText).forEach(function(element,index){
				createSortedInboxRow(element,index);
			});
		}
	};
	xhttp.send(JSON.stringify({ columnName:newSortComponent,isAsc:isAsc }));
};

function createSortedInboxRow(data,index){
	var ulTag = document.getElementsByTagName('ul')
	ulTag.item(0).replaceChild(createTags(data),ulTag.item(0).getElementsByTagName('a')[index+1]);
}

function createInboxRow(data){
	var ulTag = document.getElementsByTagName('ul');
	ulTag.item(0).appendChild(createTags(data));	
}

function createTags(data){
	var aTag = document.createElement('a');
	aTag.className = "list-group-item";
	aTag.setAttribute('style','cursor:pointer');
	for(var key in data){ 
		if(data.hasOwnProperty(key)){
			var spanTag = document.createElement('span');
			spanTag.className='name';
			spanTag.setAttribute('style','min-width: 170px; display: inline-block;text-align: center;');
			if(data[key] ===0 || data[key] === null || data[key] ===''){
				spanTag.innerText='-';				
			} else{
				spanTag.innerText=data[key];
			}
			aTag.appendChild(spanTag);
		}
	}
	var iconSpanTag = document.createElement('span');
	iconSpanTag.className="glyphicon glyphicon-pencil";
	iconSpanTag.setAttribute('style','display:none;');
	iconSpanTag.setAttribute('onclick','editMode(this)');
	aTag.appendChild(iconSpanTag);
	return aTag;
}

$(document).ready(function(){
	$('ul').on('mouseover', 'a', function() {
		if(!$(this).is(':first-child')){
			this.lastChild.setAttribute('style','display:inline-block;');
		}

	});
	$('ul').on('mouseleave', 'a', function() {
		if(!$(this).is(':first-child')){
			this.lastChild.setAttribute('style','display:none;');
		}

	});

	$('#create-button').on('click','button',function() {
		window.location.href = 'manage';
	});
	
$('span').on('click','span',function() {
	console.log('click');
});
});
document.addEventListener('DOMContentLoaded', loadPortal, false);

