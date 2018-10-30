var countriesDisplayed = false;
var yearsDisplayed = false;
var statsDisplayed = false;
initialize();

function initialize() {
	initializeCountriesDiv();
	var countries_button = document.getElementById('Select-Countries-Button');
	if (countries_button) {
		countries_button.onclick = onCountriesButtonClicked;
	}
	initializeYearsDiv();
	var selectYearButton = document.getElementById('Select-Years-Button');
	if (selectYearButton) {
		selectYearButton.onclick = onYearsButtonClicked;
	}
	initializeStatsDiv();
	var selectStatsButton = document.getElementById('Select-Stats-Button');
	if (selectStatsButton) {
		selectStatsButton.onclick = onStatsButtonClicked;
	}
	var SubmitButton = document.getElementById('Submit-Button');
	if (SubmitButton) {
		SubmitButton.onclick = onSubmitButtonClicked;
	}
	getCountryListNavbar();
	initializeBackButton();
}

function initializeBackButton(){
    var url = getBaseURLSite();
    var backButton = document.getElementById('backButton');
    if (backButton){
        backButton.href = url;
    }
}

function getBaseURL() {
	var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
	return baseURL;
}

function getBaseURLSite() {
	var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + port;
	return baseURL;
}

function initializeYearsDiv(){
	var checkboxes = "";
	for (var k = 1960; k < 2018; k++) {
		checkboxes += '<label><input type="checkbox" name="year_vals" value="'+k+'"/>'+k+'</label>';
		if (k%10 === 9){
			checkboxes += '<br>'
		}
	}

	var yearsDiv = document.getElementById('Select-Years-Div');
	if (yearsDiv) {
		yearsDiv.innerHTML = checkboxes;
		yearsDiv.style.display = "none";
	}
}

function onYearsButtonClicked(){
	var yearsDiv = document.getElementById('Select-Years-Div');
	if (yearsDiv) {
		if (yearsDisplayed){
			yearsDiv.style.display = "none";
		}
		else {
			yearsDiv.style.display = null;
		}
	}
	yearsDisplayed = !yearsDisplayed;
}

function initializeCountriesDiv() {
	var url = getBaseURL() + '/countries/';
	fetch(url, {method: 'get'})
	.then((response) => response.json())
	.then(function(countries_list) {
		var checkboxes = '<table><tr>';
		for (var k = 0; k < countries_list.length; k++) {
			cur_country_name = countries_list[k]['country_name']
			checkboxes += '<td><label><input type="checkbox" name="country_vals" value="'+cur_country_name+'"/>'+cur_country_name+'</label></td>';
			if (k%4 === 3){
				checkboxes += '</tr><tr>'
			}
		}
		checkboxes += '</tr></table>';
		
		var countriesDiv = document.getElementById('Select-Countries-Div');
		if (countriesDiv) {
			countriesDiv.innerHTML = checkboxes;
			countriesDiv.style.display = "none";
		}
		
	})
	.catch(function(error) {
		console.log(error);
	});
}

function onCountriesButtonClicked(){
	var countriesDiv = document.getElementById('Select-Countries-Div');
	if (countriesDiv) {
		if (countriesDisplayed){
			countriesDiv.style.display = "none";
		}
		else {
			countriesDiv.style.display = null;
		}
	}
	countriesDisplayed = !countriesDisplayed;
}


function initializeStatsDiv() {
	var url = getBaseURL() + '/stats/';
	fetch(url, {method: 'get'})
	.then((response) => response.json())
	.then(function(stat_list) {
		var checkboxes = '';
		for (var k = 0; k < stat_list.length; k++) {
			cur_stat_name = stat_list[k]['stat_name']
			checkboxes += '<label><input type="checkbox" name="stat_vals" value="'+cur_stat_name+'"/>'+cur_stat_name+'</label>';
			checkboxes += '<br>'
		}

		var statsDiv = document.getElementById('Select-Stats-Div');
		if (statsDiv) {
			statsDiv.innerHTML = checkboxes;
			statsDiv.style.display = "none";
		}
		
	})
	.catch(function(error) {
		console.log(error);
	});
}

function onStatsButtonClicked(){
	var statsDiv = document.getElementById('Select-Stats-Div');
	if (statsDiv) {
		if (statsDisplayed){
			statsDiv.style.display = "none";
		}
		else {
			statsDiv.style.display = null;
		}
	}
	statsDisplayed = !statsDisplayed;
}

function getCountryListNavbar() {
	var url = getBaseURL() + '/countries/';

	fetch(url, {method: 'get'})
	.then((response) => response.json())
	.then(function(countries_list) {
		var navbar = '';
		for (var k = 0; k < countries_list.length; k++) {
			navbar += '<li>';
			button_url = getBaseURLSite()+'/country/'+countries_list[k]['country_name']
			navbar += '<a href="'+button_url+'">'
			navbar += countries_list[k]['country_name']
			navbar += '</a>'
			navbar += '</li>'
		}
		var navbarElement = document.getElementById('vert-navbar');
		if (navbarElement) {
			navbarElement.innerHTML = navbar;
		}
	})

	.catch(function(error) {
		console.log(error);
	});
}

function getCountryInputfromUser() {
	var selectedCountries = [];
	var listOfCheckboxes = document.getElementsByName("country_vals");
	for (i=0; i<listOfCheckboxes.length; i++){
		if (listOfCheckboxes[i].checked === true){
			selectedCountries.push(listOfCheckboxes[i].value)
		}
	}
	selectedCountries = selectedCountries.sort();
	return selectedCountries;
}

function getStatInputfromUser() {
	var selectedStats = [];
	var listOfCheckboxes = document.getElementsByName("stat_vals");
	for (i=0; i<listOfCheckboxes.length; i++){
		if (listOfCheckboxes[i].checked === true){
			selectedStats.push(listOfCheckboxes[i].value)
		}
	}
	selectedStats = selectedStats.sort();
	return selectedStats;
}

function getYearInputfromUser() {
	var selectedYears = [];
	var listOfCheckboxes = document.getElementsByName("year_vals");
	var startYear = document.getElementById('start-year').value;
	var endYear = document.getElementById('end-year').value;
	for (i=0; i<listOfCheckboxes.length; i++){
		if (listOfCheckboxes[i].checked === true){
			selectedYears.push(listOfCheckboxes[i].value)
		}
	}
	if (startYear !== "" && endYear !== ""){
		for (year = startYear; year <= endYear; year++){
			selectedYears.push(year.toString())
		}
	}
	selectedYears = selectedYears.sort();
	return selectedYears;
}

function onSubmitButtonClicked() {
	country_input = getCountryInputfromUser();
	stat_input = getStatInputfromUser();
	year_input = getYearInputfromUser();
	
	if (country_input.length === 0){
		alert("Please select at least one country!");
		return
	}
	
	if (year_input.length === 0){
		for (year = 1960; year <= 2017; year++){
			year_input.push(year.toString())
		}
	}
	
	table_dict = []
	for (k = 0; k < country_input.length; k++){
		cur_country_name = country_input[k]
		table_dict[cur_country_name] = '<h3>'+cur_country_name+"</h3>";
		table_dict[cur_country_name] += ('<table>');
	
		//Creates the year headers table
		table_dict[cur_country_name] += '<tr>'
		table_dict[cur_country_name] += '<th></th>'
		for (var i = 0; i < year_input.length; i++) {
			table_dict[cur_country_name] += '<th>'+year_input[i]+'</th>';
		}
		table_dict[cur_country_name] += '</tr>';
	}
	
	var url = getBaseURL() + '/data/?';
	for (i=0; i< year_input.length; i++){
		url += 'year='+year_input[i]+'&';
	}
	
	var stat_id_url = getBaseURL() + '/stats/'
	fetch(stat_id_url, {method: 'get'})
	.then((response) => response.json())
	.then(function(stat_id_list) {
		
		var country_id_url = getBaseURL() + '/countries/'
		fetch(country_id_url, {method: 'get'})
		.then((response) => response.json())
		.then(function(country_id_list) {

			//Turns stat names into stat ids
			for (i=0; i< stat_input.length; i++){
				var cur_stat_id = -1;
				for (var j = 0; j < stat_id_list.length; j++) {
					if (stat_input[i] === stat_id_list[j]['stat_name']) {
						cur_stat_id = stat_id_list[j]['stat_id']
					}
				}
				url += 'stat_id='+cur_stat_id.toString()+'&';
			}
			//Turns country names into country ids
			for (i=0; i< country_input.length; i++){
				var cur_country_id = -1;
				for (var j = 0; j < country_id_list.length; j++) {
					if (country_input[i] === country_id_list[j]['country_name']) {
						cur_country_id = country_id_list[j]['country_id']
					}
				}
				url += 'country_id='+cur_country_id.toString()+'&';			
			}
			
			fetch(url, {method: 'get'})
			.then((response) => response.json())
			.then(function(data_list) {

				for (var k = 0; k < data_list.length; k++) {
					cur_data_dict = data_list[k];
					
					cur_country_id = cur_data_dict['country_id']
					cur_country_name = "fail"
					for (var i = 0; i < country_id_list.length; i++) {
						if (cur_country_id === country_id_list[i]['country_id']) {
							cur_country_name = country_id_list[i]['country_name']
						}
					}
					
					cur_stat_id = cur_data_dict['stat_id'];
					cur_stat_name = 'fail'
					for (var i = 0; i < stat_id_list.length; i++) {
						if (cur_stat_id === stat_id_list[i]['stat_id']) {
							cur_stat_name = stat_id_list[i]['stat_name']
						}
					}
					
					var table_html = '';
					table_html += '<tr>';
					table_html += '<td>';
					table_html += cur_stat_name;
					table_html += '</td>';
					
					for (var i = 0; i < year_input.length; i++) {
						cur_key = 'year_'+year_input[i];
						cur_num = cur_data_dict[cur_key];
						if (cur_num !== null) {
							cur_num = cur_num.toString();
						}
						else {
							cur_num='None';
						}
						table_html += '<td>'+cur_num.toString()+'</td>';
					}
					table_html += '</tr>';
					table_dict[cur_country_name] += table_html;
				}
				
				final_html_string = ''
				for (k = 0; k < country_input.length; k++){
					cur_country_name = country_input[k]
					table_dict[cur_country_name] += '</table>';
					final_html_string += table_dict[cur_country_name];
				}
					
				var results_div = document.getElementById('Results-Div');
				if (results_div) {
					results_div.innerHTML = final_html_string;
				}

			})
			.catch(function(error) {
				console.log(error);
			});
		})
		.catch(function(error) {
			console.log(error);
		});
	})
	.catch(function(error) {
		console.log(error);
	});
	
}