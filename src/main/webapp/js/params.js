String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

(function() {
	var settingNames = document.getElementsByClassName("setting-name");
	var parameterTBody;
	var selectElement;
	for (var i=0;i<settingNames.length;i++)
	{
		//innerText is not valid in Firefox, Safari and Opera, so using attr textContent
		innerText = (settingNames[i].textContent).trim();
		parameterTBody = settingNames[i].parentElement.parentElement;
		if(innerText=="PARAMONE"){
			selectElement = settingNames[i].parentElement.getElementsByTagName("select");
			selectElement[0].setAttribute("onchange", "paramoneChange(this)");
			selectElement[0].setAttribute("id", "paramone");
		}
		else if(innerText=="PARAMONEOne"){
			parameterTBody.setAttribute("id", "paramoneoneTBody");
			parameterTBody.style.display="none";
		}
		if(innerText=="PARAMONETwo"){
			parameterTBody.setAttribute("id", "paramonetwoTBody");
			parameterTBody.style.display="none";
		}
	}
	
	fireEvent(document.getElementById("paramone"),"change");
})();

function paramoneChange(object)
{
	if(object.value=="One"){
		document.getElementById("paramoneoneTBody").style.display="";
		document.getElementById("paramonetwoTBody").style.display="none";
	}
	else if(object.value=="Two"){
		document.getElementById("paramoneoneTBody").style.display="none";
		document.getElementById("paramonetwoTBody").style.display="";
	}
	else{
		document.getElementById("paramoneoneTBody").style.display="none";
		document.getElementById("paramonetwoTBody").style.display="none";
	}
}


