var display = false;
initialize();

function initialize() {
    var selectYearButton = document.getElementById('select-year');
    if (selectYearButton) {
        selectYearButton.onclick = onSelectYearClicked;
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

        var selectedYears = [];
        var listOfCheckboxes = document.getElementsByName("yearCheckbox");
        var startYear = document.getElementById('start-year').value;
        var endYear = document.getElementById('end-year').value;
        for (checkbox in listOfCheckboxes){
            if (checkbox.checked == true){
              selectedYears.push(checkbox.value)
            }
        }
        for (year = startYear; year <= endYear; year++){
          selectedYears.push(year)
        }
        selectedYears = selectedYears.sort();
      return selectedYears;
    }


  /*function SubmitButtonClicked() {
    url = getBaseURL() + "/data/";

    if (listOfCountries.length != 0){
      for(country in listOfCountries) {
        url += "country_name=" + country + "&";
      }
    }

    if len(listofData != 0){
      for(country in listOfCountries) {
        url += "stat_name=" + country + "&";
      }
    }

    fetch(url, {method: 'get'})

        .then((response) => response.json())
        .then(function(listOfCountries) {
            // Build the table body.
            var tableBody = '';
            for (var k = 0; k < authorsList.length; k++) {
                tableBody += '<tr>';

                tableBody += '<td><a onclick="getAuthor(' + authorsList[k]['id'] + ",'"
                                + authorsList[k]['first_name'] + ' ' + authorsList[k]['last_name'] + "')\">"
                                + authorsList[k]['last_name'] + ', '
                                + authorsList[k]['first_name'] + '</a></td>';

                tableBody += '<td>' + authorsList[k]['birth_year'] + '-';
                if (authorsList[k]['death_year'] != 0) {
                    tableBody += authorsList[k]['death_year'];
                }
                tableBody += '</td>';
                tableBody += '</tr>';
            }

            // Put the table body we just built inside the table that's already on the page.
            var resultsTableElement = document.getElementById('results_table');
            if (resultsTableElement) {
                resultsTableElement.innerHTML = tableBody;
            }
        })

        // Log the error if anything went wrong during the fetch.
        .catch(function(error) {
            console.log(error);
        });
    }

  function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
  }

  function initialize() {
      var submit = document.getElementById('submit');
      submit.onclick = submit;
  }*/
window.onload = initialize;
