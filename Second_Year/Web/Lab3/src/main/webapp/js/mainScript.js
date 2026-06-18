document.addEventListener("DOMContentLoaded", () => {
	updateSvgScale();
	
});


function updateSVGPoint(selectedR){
	
	const svg = document.querySelector("svg");
	    if (!svg) {
	        console.log("SVG не найден");
	        return;
	    }
	
	svg.querySelectorAll("circle").forEach(point =>{
		console.log("до:", point.getAttribute("fill"));
		console.log("перерисовка точек");
		let dataX = parseFloat(point.dataset.x);
		console.log(dataX);
		let dataY = parseFloat(point.dataset.y);		
		console.log(dataY);

		console.log(selectedR);
		
		let hit = checkHit(dataX, dataY, selectedR);
		
		if(hit){
			point.setAttribute("fill", "green");
		}
		else {
			point.setAttribute("fill", "red");
		}
		
		console.log("после:", point.getAttribute("fill"));
	});
		

}


function handleGraphClick(event) {
    const svg = event.currentTarget;

    const rInput = document.querySelector('[id*="change_r"]').value;
    if (!rInput || !isRValid(rInput)) {
        showToast("Выберите корректное значение R.", "error");
        return;
    }

    const center = 275;
    const scale = 50;
    const rect = svg.getBoundingClientRect();

	const x = (event.clientX - rect.left - center) / scale;
	const y = (center - (event.clientY - rect.top)) / scale;
	const r = parseFloat(rInput);
	
    console.log('Graph click - X:', x, 'Y:', y);

    if (y < -5 || y > 5 || x < -5 || x > 5) {
        showToast("Клик по области со значениями вне диапазона");
        return;
    }

    document.querySelector('[id*="graph_x"]').value = x;
    document.querySelector('[id*="graph_y"]').value = y;
	document.querySelector('[id*="graph_r"]').value = r;
	
    submitFromGraph();
}


function updateSvgScale() {
	
	let selectedR = parseFloat(document.getElementById("input-form:change_r").value);
	
	if(!isRValid(selectedR)){
		return;
	}
	
	console.log(selectedR);
	
	
    const svg = document.querySelector("svg");
    if (!svg) return;

    const center = 275;
    const baseScale = 50;
    const scale = baseScale * parseFloat(selectedR); 

	const rect = svg.querySelector("rect");
	if (rect) {
	    rect.setAttribute("x", center - scale/2);
	    rect.setAttribute("y", center);
	    rect.setAttribute("width", scale/2);
	    rect.setAttribute("height", scale);
	}

	const path = svg.querySelector("path");
	if (path) {
	    path.setAttribute(
	        "d",
	        `M${center},${center} L${center},${center - scale/2} A${scale/2},${scale/2} 0 0,0 ${center - scale/2},${center}`
	    );
	}

	const polygon = svg.querySelector("polygon");
	if (polygon) {
	    polygon.setAttribute(
	        "points",
	        `${center},${center} ${center},${center - scale/2} ${center + scale/2},${center}`
	    );
	}
	
	svg.querySelectorAll('line[data-value]').forEach(line => {
	        const value = parseFloat(line.dataset.value);
	        if (line.dataset.axis === 'x') {
	            line.setAttribute('x1', center + value * scale);
	            line.setAttribute('x2', center + value * scale);
	        } else {
	            line.setAttribute('y1', center - value * scale);
	            line.setAttribute('y2', center - value * scale);
	        }
	    });

	svg.querySelectorAll('text[data-value]').forEach(text => {
	        const value = parseFloat(text.dataset.value);
	        if (text.dataset.axis === 'x') {
	            text.setAttribute('x', center + value * scale);
	        } else {
	            text.setAttribute('y', center - value * scale);
	        }
	    });
	
	updateSvgLabels(selectedR);	
			
	updateSVGPoint(selectedR);
}


function updateSvgLabels(r){
	
	
	
	let halfRPosTexts = document.querySelectorAll("text.r-half-pos");
	halfRPosTexts.forEach(text => {
		text.textContent = parseFloat((parseFloat(r)/2).toFixed(4));
	});
	
	let rPosTexts = document.querySelectorAll("text.r-pos");
		rPosTexts.forEach(text => {
			text.textContent = parseFloat(parseFloat(r).toFixed(4));
	});
	
	let halfRNegTexts = document.querySelectorAll("text.r-half-neg");
		halfRNegTexts.forEach(text => {
			text.textContent = parseFloat((parseFloat(-r)/2).toFixed(4));
	});
		
	let rNegTexts = document.querySelectorAll("text.r-neg");
		rNegTexts.forEach(text => {
			text.textContent = parseFloat(parseFloat(-r).toFixed(4));
	});
}


function checkHit(x, y, r){
	let hit = false;
	if(y<=0 && x<= 0 && x>=-r/2 && y>=-r) hit =true;
	if(y>=0 && x>=0 && y<= r/2 - x) hit = true;
	if(y>=0 && x<=0 && x*x+y*y<r/2*r/2) hit =true;
	
	return hit;	
}

function isRValid(r) {
    if (isNaN(r)) return false;    
    if (r <= 0) return false;
    if (r < 1 || r > 4) return false; 
    return true;
}



function showToast(message, status = "error") {
  const container = document.querySelector("#toast-container tbody");
  const row = document.createElement("tr");
  const cell = document.createElement("td");
  
  if(status === "error"){
    cell.setAttribute("class", "error");
  }
  else if(status === "success"){
    cell.setAttribute("class", "success");
  }
  
  cell.innerText = message;
  row.appendChild(cell);
  container.appendChild(row);

  row.classList.add("toast-row");
  
    row.addEventListener('animationend', () => {
      row.remove();
    });

}

