function handleError(reason) {
  console.log(reason);
  if (reason && JSON.parse(reason.responseText).details) {
    let resp = JSON.parse(reason.responseText).details;
    let div = $('#errorAlert');
    div.empty();
    $.each(resp, function(index, value) {
      div.append(`${value}<br>`);
    });
    div.show();
    $('#successAlert').hide();
  } else {
    $('#errorAlert')
      .text("Error happened.")
      .show();
    $('#successAlert').hide();
  }
}

$(document).ready(function() {
  var form = $("#newTr");
  form.on('click', function(event) {
    var formData = {
      name: $("#name").val(),
      from: $("#from").val(),
      to: $("#to").val(),
      distanceKm: $("#distanceKm").val(),
      carId: $("#carId").val(),
      about: $("#about").val(),
      saveToHistory: $("#saveToHistory1").prop('checked'),
    };

    $.ajax({
      data: JSON.stringify(formData),
      type: 'POST',
      url: '/user/co2',
      dataType: "json",
      contentType: "application/json",
      encode: true,
    })
      .done(function(result) {
        console.log("done!!!");
        console.log(result);
        if (result.error) {
          console.log(result.error);
        } else {
          if (result) {
            const co2text = `Your trip from [ ${result.from} ] to [ ${result.to} ] driving 
             the car [ ${result.carName} ] with average fuel consumption [ ${result.fuelConsumptionComb} ] 
             will result in total 
             Carbon Footprint of [ ${parseFloat(result.co2Amount).toFixed(2)} ] g/km 
             which also is  [ ${parseFloat(result.co2Amount/1000).toFixed(2)} ] kg/km .`;
            $('#successCO2').text(co2text);
            $(".popup").fadeIn(500);
            $('#errorAlert').hide();
            // $('#successAlert').css({ 'display': 'block' });
          } else {
            $('#errorAlert')
              .text("No records found.")
              .show();
            // $('#successAlert').hide();
          }
        }
        window.scrollTo(0, 0);
      })
      .fail(function(reason) {
        console.log("fail!!!");
        handleError(reason);
      }).then(() =>
      setTimeout(function() {
        $('#errorAlert').hide();
      }, 5000))
      .catch(function(reason) {
        console.log("catch!!!");
        handleError(reason);
      }).then(() =>
      setTimeout(function() {
        $('#errorAlert').hide();
      }, 5000));
    if (event) {
      event.preventDefault();
    }
  });
});

$(".close").click(function() {
  $(".popup").fadeOut(500);
});
