document.addEventListener("DOMContentLoaded", function() {
    console.log("Page loaded. Checking due dates...");

    var dueDates = document.querySelectorAll('.due-date span');
    var currentDate = new Date();
    dueDates.forEach(function(element) {
        var dueDate = new Date(element.textContent.trim()); // Convert the text content to a Date object
        var timeDiff = dueDate.getTime() - currentDate.getTime();
        var diffMinutes = Math.ceil(timeDiff / (1000 * 60)); // difference in minutes
        var diffHours = Math.floor(diffMinutes / 60); // difference in hours
        var diffDays = Math.floor(timeDiff / (1000 * 3600 * 24)); // difference in days

        var todoItem = element.closest('.todo-item');
        var checkbox = todoItem.querySelector('input[type="checkbox"]');
        var taskTitle = todoItem.querySelector('span').textContent.trim();

        var message = null; // Initialize message variable

        if (diffMinutes <= 0) { // Task is overdue
            message = "Task is overdue!";
        } else if (diffMinutes > 0 && diffMinutes <= 1440) { // less than or equal to 24 hours
            if (diffDays === 1) {
                message = "Task is due tomorrow!";
            } else if (diffHours === 1) {
                message = "Task is due in 1 hour!";
            } else if (diffHours > 1) {
                message = "Task is due in " + diffHours + " hours!";
            } else if (diffMinutes === 1) {
                message = "Task is due in one minute!";
            } else if (diffMinutes < 60) {
                message = "Task is due in " + diffMinutes + " minutes!";
            }
        }

        // Show popup only if message is defined
        if (message && checkbox && !checkbox.checked) {
            var popupShownKey = "popupShown_" + taskTitle;
            console.log("Checking sessionStorage for key:", popupShownKey);

            if (!sessionStorage.getItem(popupShownKey)) {
                console.log("Showing popup for task:", taskTitle);
                showPopup(taskTitle, message);
                sessionStorage.setItem(popupShownKey, "true");
            } else {
                console.log("Popup already shown for task:", taskTitle);
            }
        }
    });
});

function showPopup(taskTitle, message) {
    console.log("Creating popup for task:", taskTitle);
    var popup = document.createElement("div");
    popup.classList.add("popup");

    var card = document.createElement("div");
    card.classList.add("card");

    var cardBody = document.createElement("div");
    cardBody.classList.add("card-body");

    var popupTitle = document.createElement("h5");
    popupTitle.classList.add("card-title");
    popupTitle.textContent = "Task '" + taskTitle + "'";

    var popupMessage = document.createElement("p");
    popupMessage.classList.add("card-text");
    popupMessage.textContent = message;

    cardBody.appendChild(popupTitle);
    cardBody.appendChild(popupMessage);

    var closeButton = document.createElement("button");
    closeButton.setAttribute("type", "button");
    closeButton.classList.add("btn-close");
    closeButton.setAttribute("aria-label", "Close");
    closeButton.innerHTML = "&times;";
    closeButton.addEventListener("click", function() {
        popup.classList.remove("show");
        console.log("Popup closed for task:", taskTitle);
    });

    card.appendChild(cardBody);
    card.appendChild(closeButton);

    popup.appendChild(card);

    document.body.appendChild(popup);

    setTimeout(function() {
        popup.classList.add("show");
        console.log("Popup shown for task:", taskTitle);
    }, 500);
}
