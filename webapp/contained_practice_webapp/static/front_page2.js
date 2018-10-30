/*
 * books.js
 * Jeff Ondich, 27 April 2016
 * Updated, 4 May 2018
 *
 * A little bit of Javascript showing one small example of AJAX
 * within the "books and authors" sample for Carleton CS257,
 * Spring Term 2017.
 *
 * This example uses a very simple-minded approach to Javascript
 * program structure, which suffers from the problem of
 * "global namespace pollution". We'll talk more about this after
 * you get a feel for some Javascript basics.
 */

// IMPORTANT CONFIGURATION INFORMATION
// The contents of getBaseURL below reflects our assumption that
// the web application (books_website.py) and the API (books_api.py)
// will be running on the same host but on different ports.
//
// But if you take a look at the contents of getBaseURL, you may
// ask: where does the value of api_port come from? The answer is
// a little bit convoluted. (1) The command-line syntax of
// books_website.py includes an argument for the API port;
// and (2) the index.html Flask/Jinja2 template includes a tiny
// bit of Javascript that declares api_port and assigns that
// command-line API port argument to api_port. This happens
// before books.js is loaded, so the functions in books.js (like
// getBaseURL) can access api_port as needed.

var countriesDisplayed = false;
initialize();

function initialize() {
    var countries_button = document.getElementById('Select-Countries-Button');
    if (countries_button) {
        countries_button.onclick = onCountriesButtonClicked;
    }
	getCountryListNavbar()
}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
}

function getBaseURLSite() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + port;
    return baseURL;
}

function onCountriesButtonClicked() {
    if (!countriesDisplayed) {
		var url = getBaseURL() + '/countries/';
		fetch(url, {method: 'get'})
		.then((response) => response.json())
		.then(function(countries_list) {
			var checkboxes = '';
			for (var k = 0; k < countries_list.length; k++) {
				cur_country_name = countries_list[k]['country_name']
				checkboxes += '<input type="checkbox" name="country_vals" value="'+cur_country_name+'">'+cur_country_name+'<br>';
			}

			var countriesDiv = document.getElementById('Select-Countries-Div');
			if (countriesDiv) {
				countriesDiv.innerHTML = checkboxes;
			}
		})
		.catch(function(error) {
			console.log(error);
		});
	} else {
		var countriesDiv = document.getElementById('Select-Countries-Div');
		if (countriesDiv) {
			countriesDiv.innerHTML = "";
		}
	}
	
	countriesDisplayed = !countriesDisplayed;
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
        var navbarElement = document.getElementById('navbar');
        if (navbarElement) {
            navbarElement.innerHTML = navbar;
        }
    })

    .catch(function(error) {
        console.log(error);
    });
}

