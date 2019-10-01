window.onload = function() {
	var dialog = document.getElementById("myDialog");
	var buttonPlanExhibition = document.getElementById("planExhibitionButton");
	var buttonConfirm = document.getElementById("confirmDialogButton");
	var buttonCancel = document.getElementById("cancelDialogButton");
	var comboBox = document.getElementById('typeComboBox');

	buttonPlanExhibition.onclick = function() {
		dialog.style.display = "block";
	}
	buttonConfirm.onclick = function() {
		window.location.href='showFormForAdd?type=' + comboBox.value.toLowerCase();
	}
	
	buttonCancel.onclick = function() {
		dialog.style.display = "none";
	}

	

	window.onclick = function(event) {
		if (event.target == dialog) {
			dialog.style.display = "none";
		}
	}
}
