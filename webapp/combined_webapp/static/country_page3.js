initialize();

function initialize() {
	changeTitle();
	changeHeader();
	makeDataTable();
}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
}

function changeTitle() {
    var titles = document.getElementsByTagName('title');
    if (titles.length > 0) {
        titles[0].innerHTML += country_name;
    }
}

function changeHeader() {
    var header = document.getElementById('country-title');
    if (header) {
        header.innerHTML += country_name;
    }
}

function makeDataTable() {
	table_html = '';

	//Creates the year headers table
	table_html += '<tr>'
	table_html += '<th></th>'
	for (var i = 1960; i < 2018; i++) {
		table_html += '<th>'+(i).toString()+'</th>';
	}
	table_html += '</tr>';
	
	
	var all_data = [];
	var url = getBaseURL() + '/data/?country_name='+country_name;
	fetch(url, {method: 'get'})
	.then((response) => response.json())
	.then(function(data_list) {
		all_data = data_list;

		var stat_id_url = getBaseURL() + '/stats/'
		var stat_id_dicts = [];
		fetch(stat_id_url, {method: 'get'})
		.then((response) => response.json())
		.then(function(stat_id_list) {
			stat_id_dicts = stat_id_list;
			
			for (var k = 0; k < all_data.length; k++) {
				table_html += '<tr>';
				
				cur_data_dict = all_data[k];
				cur_stat_id = cur_data_dict['stat_id'];
				cur_stat_name = 'fail'
				for (var i = 0; i < stat_id_dicts.length; i++) {
					if (cur_stat_id === stat_id_dicts[i]['stat_id']) {
						cur_stat_name = stat_id_dicts[i]['stat_name']
					}
				}
				table_html += '<td>';
				table_html += cur_stat_name;
				table_html += '</td>';
				
				for (var i=1960; i < 2018; i++) {
					cur_key = 'year_'+i.toString();
					cur_num = cur_data_dict[cur_key];
					if (cur_num !== null) {
						cur_num = cur_num.toString();
					}
					else {
						cur_num='';
					}
					table_html += '<td>'+cur_num.toString()+'</td>';
				}
				table_html += '</tr>';
			}
			
			var results_table = document.getElementById('results-table');
			if (results_table) {
				results_table.innerHTML = table_html;
			}

		})
		.catch(function(error) {
			console.log(error);
		});

	})
	.catch(function(error) {
		console.log(error);
	});
	
}