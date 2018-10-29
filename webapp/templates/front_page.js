/*
    sample.js
    Jeff Ondich, 5 May 2016
    A small demo of some simple Javascript techniques.
    For CS257 Software Design, Carleton College
 */
 function getInputfromUser() {
      var selectedCountries = [];
      var country = document.getElementById('Algeria');
      if country.checked {
          selectedCountries.push(country);
          SubmitButtonClicked(selectedCountries);
      }

      var dataSelected = [];
      var data = document.getElementById('gdp_usd');
      if data.checked {
          dataSelected.push(data);
      }
      SubmitButtonClicked(listOfCountries, listOfData);
  }

  function SubmitButtonClicked(listOfCountries, listOfData) {
        var url = getBaseURL() + '/countries/';

        // Send the request to the Books API /authors/ endpoint
        fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
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
