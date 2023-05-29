import React, { useState, useEffect } from "react";
import axios from "axios";
import Navigation from "../Navigation";
import Court from "../Court";
import { Button } from "../Button";
import Add from "./Add";
import loadingImage from "../loading.gif"; // Import your loading image


function ManageCourts() {
  const [dataArray, setDataArray] = useState([]);
  const [isLoading, setIsLoading] = useState(false); // Add loading state
  const [selectedOption, setSelectedOption] = useState("none"); 
  

  const handleOptionChange = (event) => {
    setSelectedOption(event.target.value);
    
  };
  const handleAdd = () => {
    window.history.pushState({}, "", "/Main/ManageCourts/Add");
    window.location.reload();
    return;
  };
  
  const handleModify = (index) => {
    const newUrl = `/Main/ManageCourts/Modify?index=${dataArray[index].id}`;
    window.history.pushState({}, "", newUrl);
    window.location.reload();
    return;
  };

  const handleDelete = async (index) => {
    //console.log(dataArray[i].id);
    await axios({
      url: "http://localhost:8080/fields",
      method: "DELETE",
      data: {
        id: dataArray[index].id,
      },
    }).catch((err) => console.log(err));
    window.location.reload();
  };

  useEffect(() => {
    let us = localStorage.getItem("username");
    setIsLoading(true);
    axios({
      url: `http://localhost:8080/owner/${us}`,
      method: "GET",
      // TODO Token?????????
    })
      .then((res) => {
        setDataArray(res.data);
        setIsLoading(false); // Set isLoading to false after the request is completed
      })
      .catch((err) => {
        console.log(err);
        setIsLoading(false); // Set isLoading to false if an error occurs
      }
      );
  
    }, []); // Empty dependency array to run the effect only once


    useEffect(() => {  
      let us = localStorage.getItem("username");
      setIsLoading(true);
      axios({
        url: `http://localhost:8080/sorted/owner/${us}/${selectedOption}`,
        method: "GET",
        // TODO Token?????????
      })
        .then((res) => {
          setDataArray(res.data);
          setIsLoading(false);
        })
        .catch((err) => {
          console.log(err);
          setIsLoading(false);
        });
    
    }, [selectedOption]); // Dependency array with selectedOption as a dependency
  

return (
    <div style={{
      
    }}>
      <div>
        <Navigation></Navigation>
      </div>
      <div
        style={{
          backgroundColor: "gray",
          position: "fixed",
          marginTop: "80px",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          width: "100%",
        }}
      >
        <button onClick={() => handleAdd()}>Add New Court!</button>
        <select value={selectedOption} onChange={handleOptionChange}>
          <option value="none">none</option>
          <option value="price asc">price asc</option>
          <option value="price desc">price desc</option>
          <option value="basketball">basketball</option>
          <option value="football">football</option>
          <option value="tennis">tennis</option>
          
        </select>
      </div>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <div
          className="courts-container"
          style={{
            marginTop: "10%",
            width: "50%",
            position: "relative",
          }}
        >
          {dataArray.map((item, index) => (
            <div key={item.key}>
              <Court obiect={item} />
              <div className="butoane">
                <button onClick={() => handleModify(index)}> Modify </button>
                <button
                  id={"buton" + index}
                  onClick={() => handleDelete(index)}
                >
                  {" "}
                  Delete{" "}
                </button>
              </div>
            </div>
          ))}
          {isLoading && (
            <div className="loading-container">
              <div className="loading-overlay" />
              <div className="loading-box">
                <img
                  src={loadingImage}
                  alt="Loading"
                  className="loading-image"
                />
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
export default ManageCourts;
