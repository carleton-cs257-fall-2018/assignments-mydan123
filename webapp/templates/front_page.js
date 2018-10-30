function clickedOnSelectYear(){
    var selectYearButton = document.getElementById('select-years');
    var years = document.getElementById('years');
    if (selectYearButton.onclick == true) {
      years.style.display = "block";
    }
    else{
      years.style.display = "none";

    }
}

function initialize() {
  var selectYearButton = document.getElementById('select-year');
    selectYearButton.onclick = selectYearButton;
}

 function getInputfromUser() {
      var selectedCountries = [];
      var country = document.getElementById('Algeria');
      if (country.checked) {
          selectedCountries.push(country);
      }

      var dataSelected = [];
      var data = document.getElementById('gdp_usd');
      if (data.checked) {
          dataSelected.push(data);
      }
      SubmitButtonClicked(listOfCountries, listOfData);
  }

  function SubmitButtonClicked(listOfCountries, listOfData) {
    url = getBaseURL() + "/data/";

    if len(listOfCountries != 0){
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
  }
window.onload = initialize;
