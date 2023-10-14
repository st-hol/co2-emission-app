// Chart.js scripts
// -- Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily =
  '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = "#292b2c";

function simulationView() {
  var ctx = document.getElementById("myAreaChart");
  var myLineChart = new Chart(ctx, {
    type: "line",
    data: {
      labels: [
        "Oct 1",
        "Oct 2",
        "Oct 3",
        "Oct 4",
        "Oct 5",
        "Oct 6",
        "Oct 7",
        "Oct 8",
        "Oct 9",
        "Oct 10",
        "Oct 11",
        "Oct 12",
        "Oct 13",
        "Oct 14",
        "Oct 15",
        "Oct 16",
        "Oct 17",
        "Oct 18",
        "Oct 19",
        "Oct 20",
        "Oct 21",
        "Oct 22",
        "Oct 23",
        "Oct 24",
        "Oct 25",
        "Oct 26",
        "Oct 27",
        "Oct 28",
        "Oct 29",
        "Oct 30",
        "Oct 31"
      ],
      datasets: [
        {
          label: "emissions (g)",
          lineTension: 0.3,
          backgroundColor: "rgba(2,117,216,0.2)",
          borderColor: "rgba(2,117,216,1)",
          pointRadius: 5,
          pointBackgroundColor: "rgba(2,117,216,1)",
          pointBorderColor: "rgba(255,255,255,0.8)",
          pointHoverRadius: 5,
          pointHoverBackgroundColor: "rgba(2,117,216,1)",
          pointHitRadius: 20,
          pointBorderWidth: 2,
          data: [
            1120,
            2120,
            500,
            900,
            3000,
            1800,
            1244,
            1444,
            423,
            141,
            0,
            0,
            0,
            0,
            422,
            900,
            1600,
            2100,
            1424,
            1244,
            4591,
            4000,
            2100,
            1300,
            0,
            0,
            0,
            0,
            0,
            0,
            0
          ]
        }
      ]
    },
    options: {
      scales: {
        xAxes: [
          {
            time: {
              unit: "day of month"
            },
            gridLines: {
              display: false
            },
            ticks: {
              maxTicksLimit: 31
            }
          }
        ],
        yAxes: [
          {
            ticks: {
              min: 0,
              max: 5000,
              maxTicksLimit: 5
            },
            gridLines: {
              color: "rgba(0, 0, 0, .125)"
            }
          }
        ]
      },
      legend: {
        display: false
      }
    }
  });
// -- Bar Chart Example
  var ctx2 = document.getElementById("myBarChart");
  var myBarChart = new Chart(ctx2, {
    type: "bar",
    data: {
      labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
      datasets: [
        {
          label: "Emissions",
          backgroundColor: "rgba(2,117,216,1)",
          borderColor: "rgba(2,117,216,1)",
          data: [1.7, 2.2, 1.5, 3.9, 1.2, 4.3, 1.7, 0.9, 1.2, 4.1, 0, 0]
        }
      ]
    },
    options: {
      scales: {
        xAxes: [
          {
            time: {
              unit: "month"
            },
            gridLines: {
              display: false
            },
            ticks: {
              maxTicksLimit: 12
            }
          }
        ],
        yAxes: [
          {
            ticks: {
              min: 0,
              max: 6,
              maxTicksLimit: 5
            },
            gridLines: {
              display: true
            }
          }
        ]
      },
      legend: {
        display: false
      }
    }
  });
// -- Pie Chart Example
  var ctx3 = document.getElementById("myPieChart");
  var myPieChart = new Chart(ctx3, {
    type: "pie",
    data: {
      labels: ["Audi A4", "BMW X5", "Ford Focus", "Kia Rio"],
      datasets: [
        {
          data: [12.21, 15.58, 11.25, 8.32],
          backgroundColor: ["#007bff", "#dc3545", "#ffc107", "#28a745"]
        }
      ]
    }
  });
}

// -- Area Chart Example
// simulationView();

function realView(result) {
  var ctx = document.getElementById("myAreaChart");
  var myLineChart = new Chart(ctx, {
    type: "line",
    data: {
      labels: [...result.currentMonthDailyExhaust.keys()],
      datasets: [
        {
          label: "emissions (g)",
          lineTension: 0.3,
          backgroundColor: "rgba(2,117,216,0.2)",
          borderColor: "rgba(2,117,216,1)",
          pointRadius: 5,
          pointBackgroundColor: "rgba(2,117,216,1)",
          pointBorderColor: "rgba(255,255,255,0.8)",
          pointHoverRadius: 5,
          pointHoverBackgroundColor: "rgba(2,117,216,1)",
          pointHitRadius: 20,
          pointBorderWidth: 2,
          data: [...result.currentMonthDailyExhaust.values()]
        }
      ]
    },
    options: {
      scales: {
        xAxes: [
          {
            time: {
              unit: "day of month"
            },
            gridLines: {
              display: false
            },
            ticks: {
              maxTicksLimit: [...result.currentMonthDailyExhaust.keys().length]
            }
          }
        ],
        yAxes: [
          {
            ticks: {
              min: result.currentMonthDailyExhaustMinY,
              max: result.currentMonthDailyExhaustMaxY,
              maxTicksLimit: 5
            },
            gridLines: {
              color: "rgba(0, 0, 0, .125)"
            }
          }
        ]
      },
      legend: {
        display: false
      }
    }
  });
// -- Bar Chart Example
  var ctx2 = document.getElementById("myBarChart");
  var myBarChart = new Chart(ctx2, {
    type: "bar",
    data: {
      labels: [...result.emissionsByMonths.keys()],
      datasets: [
        {
          label: "Emissions",
          backgroundColor: "rgba(2,117,216,1)",
          borderColor: "rgba(2,117,216,1)",
          data: [...result.emissionsByMonths.values()]
        }
      ]
    },
    options: {
      scales: {
        xAxes: [
          {
            time: {
              unit: "month"
            },
            gridLines: {
              display: false
            },
            ticks: {
              maxTicksLimit: result.emissionsByMonths.keys().length
            }
          }
        ],
        yAxes: [
          {
            ticks: {
              min: result.emissionsByMonthsMinY,
              max: result.emissionsByMonthsMaxY,
              maxTicksLimit: 5
            },
            gridLines: {
              display: true
            }
          }
        ]
      },
      legend: {
        display: false
      }
    }
  });
// -- Pie Chart Example
  var ctx3 = document.getElementById("myPieChart");
  var myPieChart = new Chart(ctx3, {
    type: "pie",
    data: {
      labels: [...result.carUsageFrequencyToPercents.keys()],
      datasets: [
        {
          data: [...result.carUsageFrequencyToPercents.values()],
          backgroundColor: ["#007bff", "#dc3545", "#ffc107", "#28a745"]
        }
      ]
    }
  });
}

$(document).ready(function() {
    $.ajax({
      type: 'GET',
      url: '/user/stats',
      dataType: "json",
      contentType: "application/json",
      encode: true,
    })
      .done(function(result) {
        console.log("done!!!");
        console.log(result);
        if (result.error) {
          console.log(result.error);
          simulationView();
        } else {
          if (result && !result.simulationDemoModeEnabled) {
            realView(result);
            // $('#successAlert').css({ 'display': 'block' });
          } else {
            simulationView();
          }
        }
        window.scrollTo(0, 0);
      })
      .fail(function(reason) {
        console.log("fail!!!");
        simulationView();
        // handleError(reason);
      }).then(() =>
      setTimeout(function() {
        $('#errorAlert').hide();
      }, 5000))
      .catch(function(reason) {
        console.log("catch!!!");
        simulationView();
        // handleError(reason);
      }).then(() =>
      setTimeout(function() {
        // $('#errorAlert').hide();
      }, 5000));
    if (event) {
      event.preventDefault();
    }
});

