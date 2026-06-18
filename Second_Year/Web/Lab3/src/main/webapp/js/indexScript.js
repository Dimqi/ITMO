
function renderClock() {
    const hiddenField = document.getElementById('clockForm:hiddenTimeField');
    if (!hiddenField || !hiddenField.value) return;

    const partsOfTime = hiddenField.value.split(":");
    const hr = parseInt(partsOfTime[0], 10);
    const min = parseInt(partsOfTime[1], 10);
    const sec = parseInt(partsOfTime[2], 10);

    const secDeg = sec * 6; // 360° / 60
    const minDeg = min * 6 + sec * 0.1; 
    const hrDeg = (hr % 12) * 30 + min * 0.5;

    document.getElementById('second-hand').style.transform = `rotate(${secDeg-90}deg)`;
    document.getElementById('minute-hand').style.transform = `rotate(${minDeg-90}deg)`;
    document.getElementById('hour-hand').style.transform = `rotate(${hrDeg-90}deg)`;
	
	const hiddenDate = document.getElementById('clockForm:hiddenDateField');
	const visibleDate = document.getElementById('clockForm:visibleDate');
	if (hiddenDate && visibleDate) {
	    visibleDate.textContent = hiddenDate.value;
	}
}

function initClock() {
    if (typeof updateClockAjax !== 'function') {
        console.error("updateClockAjax() не найдено! Проверьте p:remoteCommand.");
        return;
    }
    updateClockAjax();
    setInterval(updateClockAjax, 2000);
}

document.addEventListener("DOMContentLoaded", initClock);



