var display = false;
initialize();

function initialize() {
    var selectYearButton = document.getElementById('select-year');
    if (selectYearButton) {
        selectYearButton.onclick = onSelectYearClicked;
    }

    var submitButton = document.getElementById('submit');
    if (submitButton) {
        submitButton.onclick = onSubmitClicked;
    }
    getCountryListNavbar();
}

function onSelectYearClicked(){
  if (!display){
    var checkboxes = "";
    for (var k = 1960; k < 2018; k++) {
      checkboxes += '<input type="checkbox" name="yearCheckbox" value="'+k+'">'+k+'</input><br>';
    }

    var yearsDiv = document.getElementById('all-years');
    if (yearsDiv) {
      yearsDiv.innerHTML = checkboxes;
    }
  }
    else {
      var yearsDiv = document.getElementById('all-years');
      if (yearsDiv) {
        yearsDiv.innerHTML = "";
      }
    }
  display = !display;
}

function getCountryListNavbar() {
    var url = "http://perlman.mathcs.carleton.edu:5104/countries/";
    //var url = getBaseURL() + '/countries/';
    fetch(url, {method: 'get'})
    .then((response) => response.json())
    .then(function(countries_list) {
        var navbar = '';
        for (var k = 0; k < countries_list.length; k++) {
            navbar += '<li>';
			      navbar += '<a href="https://www.google.com">'
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
   var listOfCheckboxes = document.getElementsByName("countryCheckbox");
   for (checkbox in listOfCheckboxes){
       if (checkbox.checked == true){
         selectedCountries.push(checkbox.value)
       }
   }
   selectedCountries = selectedCountries.sort();
   return selectedCountries;
  }

  function getDataInputfromUser() {
       var selectedData = [];
       var listOfCheckboxes = document.getElementsByName("dataCheckbox");
       for (checkbox in listOfCheckboxes){
           if (checkbox.checked == true){
             selectedData.push(checkbox.value)
           }
       }
       selectedData = selectedData.sort();
     return selectedData;
   }

   function getSpecifiedYearsfromUser() {

        var finalListOfYearsSelected = [];
        var userSelectedYears = [];
        var startYear = document.getElementById('start-year').value;
        var endYear = document.getElementById('end-year').value;

        var listOfCheckboxes = document.getElementsByName("yearCheckbox");
        for (checkbox in listOfCheckboxes){
            if (checkbox.checked == true){
              userSelectedYears.push(checkbox.value)
            }
        }
        userSelectedYears = userSelectedYears.sort();

        finalListOfYearsSelected.push(startYear);
        finalListOfYearsSelected.push(endYear);
        finalListOfYearsSelected.push(userSelectedYears);
    }

    function onSubmitClicked(){

        var url = getBaseURL() + "/data/?";

        var dataParameters = getDataInputfromUser();
        var countryParameters = getCountryInputfromUser();
        var yearParameters = getSpecifiedYearsfromUser();

        var startYear = yearParameters[0];
        var endYear = yearParameters[1];
        var listOfYearsSelected = yearParameters[3];

        if (dataParameters.length != 0){
            for (data in dataParameters){
                url += "stat_name=" + data + "&";
            }
        }

        if (countryParameters.length != 0){
            for (country in countryParameters){
                url += "country_name=" + country + "&";
            }
        }

        if (listOfYearsSelected.length != 0){
            for (year in listOfYearsSelected){
                url += "year=" + year + "&";
            }
        }

        url += "start_year=" + startYear + "&";
        url += "end_year=" + endYear;

        fetch(url, {method: 'get'})

            .then((response) => response.json())
            .then(function(listOfdictionaries) {
                var tableBody = '';

                //Table goes here


                var resultsTableElement = document.getElementById('outputted-data');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
            })

            .catch(function(error) {
                console.log(error);
            });
    }

  function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
  }
window.onload = initialize;
