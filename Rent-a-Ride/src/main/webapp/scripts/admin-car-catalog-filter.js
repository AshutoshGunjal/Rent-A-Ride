function setInputValue(type) {
	switch (type) {
	case 'licensePlateNumber':
		var licensePlateNumberDropdown = document
				.getElementById("licensePlateNumber");
		var licensePlateNumber = licensePlateNumberDropdown.options[licensePlateNumberDropdown.selectedIndex].value;
		var licensePlateNumberInput = document
				.getElementById("licensePlateNumberInput");
		licensePlateNumberInput.setAttribute('value', licensePlateNumber);
		break;

	case 'drivingLicenseNumber':
		var drivingLicenseNumberDropdown = document
				.getElementById("drivingLicense");
		var drivingLicenseNumberFilter = drivingLicenseNumberDropdown.options[drivingLicenseNumberDropdown.selectedIndex].value;
		var drivingLicenseNumberInput = document
				.getElementById("drivingLicenseNumberInput");
		drivingLicenseNumberInput.setAttribute('value',
				drivingLicenseNumberFilter);
		break;

	}
}