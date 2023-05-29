import React, { useState, useEffect } from "react";
import axios from "axios";
import Navigation from "../Navigation";
import Court from "../Court";
import { Button } from "../Button";
import "./Add.css"
import loadingImage from "../loading.gif"; // Import your loading image

function Add({ obiect }) {
  const [description, setDescription] = useState("");
  const [city, setCity] = useState("");
  const [address, setAddress] = useState("");
  const [Basket, setBasket] = useState(false);
  const [Fotbal, setFotbal] = useState(false);
  const [Tenis, setTenis] = useState(false);
  const [pret, setPret] = useState(null);
  const [selectedFile, setSelectedFile] = useState(null);
  const [totok, setTotok] = useState(false);
  const [isLoading, setIsLoading] = useState(false); // Add loading state

  const checkAll = () => {
    if (
      description === "" ||
      city === "" ||
      address === "" ||
      pret === null ||
      selectedFile === null
    ) {
      setTotok(false);
      return 0;
    } else {
      setTotok(true);
      return 1;
    }
  };
  const handleSubmit = async (event) => {
    event.preventDefault();
    console.log(checkAll());
    setIsLoading(true); // Set loading state to true
    // let fileData = new FormData();
    // fileData.append('fileData', selectedFile);
    //console.log(Basket, Fotbal, Tenis);
    if (checkAll()) {
      const formData = new FormData();
      formData.append('owner', localStorage.getItem('username'));
      formData.append('city', city);
      formData.append('address', address);
      formData.append('description', description);
      formData.append('price', pret);
      formData.append('basketball', Basket);
      formData.append('football', Fotbal);
      formData.append('tennis', Tenis);
      formData.append('fileData', selectedFile);
      try {
        await axios.post('http://localhost:8080/fields', formData, {
        headers: {
          Authorization: localStorage.getItem("token"),
        },
      });
        window.history.pushState({}, "", "/Main/ManageCourts");
        window.location.reload();
      } catch (err) {
        console.log(err);
      }
      setIsLoading(false); // Set loading state back to false
    } else {
      setIsLoading(false);
      return;
    }
  };

  const handleFileInputChange = (event) => {
    //console.log(event.target);
    setSelectedFile(event.target.files[0]);
  };
  // TODO document
  return (
    
    <div>
      <div>
        <Navigation></Navigation>
      </div>
      <div
        style={{
          
        }}
      >
        <div className="addSection">
        <form className="add-form">
          <label className="labelAdd"> Field Description </label>
          <textarea className="textAreaAdd"
            value={description}
            onChange={(e) => {
              setDescription(e.target.value);
              checkAll();
            }}
            placeholder="Enter the description"
          ></textarea>

          <label className="labelAdd">City</label>
          <input className="city-input"
            value={city}
            onChange={(e) => {
              setCity(e.target.value);
              checkAll();
            }}
            type="text"
            placeholder="Enter the city"
          ></input>

          <label className="labelAdd">Address</label>
          <input className="address-input"
            value={address}
            onChange={(e) => {
              setAddress(e.target.value);
              checkAll();
            }}
            type="text"
            placeholder="Enter the address"
          ></input>

          <div className="checkbox-container">
          <label className="labelAdd" htmlFor="basket-checkbox">Basket</label>
       
            <input className="basket-input"
              value={Basket}
              onChange={(e) => {
                setBasket(e.target.checked);
                checkAll();
              }}
              type="checkbox"
            />
           </div>

           <div className="checkbox-container">
          <label className="labelAdd" htmlFor="fotbal-checkbox">Football</label>
       
            <input className="basket-input"
              value={Fotbal}
              onChange={(e) => {
                setFotbal(e.target.checked);
                checkAll();
              }}
              type="checkbox"
            />
           </div>
           <div className="checkbox-container">
          <label className="labelAdd" htmlFor="tenis-checkbox">Tennis</label>
       
            <input className="tennis-input"
              value={Tenis}
              onChange={(e) => {
                setTenis(e.target.checked);
                checkAll();
              }}
              type="checkbox"
            />
           </div>
          <label for="number-input" className="labelAdd">
            Price
          </label>
          <input className="pret-input"
            value={pret}
            onChange={(e) => {
              const value = e.target.value.replace(/[^0-9]/g, "");
              setPret(value);
              checkAll();
            }}
            type="text"
            placeholder="Enter the price"
          ></input>

          <label for="number-input" className="labelAdd">
            Document
          </label>
          <input className="doc-input"
            onChange={(e) => {
              //console.log(e);
              //console.log(e.target);
              handleFileInputChange(e);
            }}
            type="file"
            accept=".pdf"
          ></input>
        {totok === false && (
          <div
            style={{
              alignSelf: "center",
              maxWidth: "200px",
              textAlign: "center",
            }}
          >
            <p
              style={{
                color: "red",
                alignSelf: "center",
                fontSize: "0.85rem",
              }}
            >
              All field must be completed!{" "}
            </p>
          </div>
        )}
        <button className="btn btn-outline btn--medium" onClick={handleSubmit}>
          Add the court!
        </button>
        
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
        </form>
        </div>
      </div>
    </div>
  );
}
export default Add;
