var counter = 30;
var timerElement = document.getElementById("timer");

function setTimer() {

	var timerId = setInterval(countdown, 1000);
}


function countdown() {


	if(counter == 0){
		timerElement.innerHTML = "Time Up!!";
	}
	else if  (counter < 5) {
		timerElement.innerHTML = counter + ' seconds remaining' + "...Time ALmost Over!!";
		counter--;
	} 

	else {
		timerElement.innerHTML = counter + ' seconds remaining';
		counter--;
	}
}