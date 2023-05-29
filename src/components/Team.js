import React from "react";
import "./Team.css";

function Team({ obiect, key }) {
  var id = obiect.id;
  var location = obiect.location;
  var date = obiect.date;
  var sport = obiect.sport;
  var players_needed = obiect.players_needed;
  var price = obiect.price;
  var name = obiect.name;

  return (
    <div className="team-div">
      <h2>Team: {name}</h2>
      <p>Location: {location}</p>
      <p>Date: {date}</p>
      <p>Sport: {sport}</p>
      <p>Players Needed: {players_needed}</p>
      <p>Price: {price}</p>
    </div>
  );
}

export default Team;
