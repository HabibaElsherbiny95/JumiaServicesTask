import React, { useMemo, useState, useEffect } from "react";
import axios from "axios";
import { Container } from "reactstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import Table from "./Table";

const phoneNumbersUrl = "http://localhost:8080/api/v1/phoneNumbers";

function App() {
  const [phoneNumbers, setPhoneNumbers] = useState({});
  const [inputs, setInputs] = useState({
    countryName: null,
    state: "NONE"
  })

  useEffect(() => {
    getPhoneNumbersWithAxios();
  }, []);

  const getPhoneNumbersWithAxios = async (countryName, state) => {
    setPhoneNumbers(null)
    const response = await axios.get(phoneNumbersUrl, 
      { params: {
        countryName: countryName !== "" ? countryName : null,
        state: state
      },  crossdomain: true
    });
    setPhoneNumbers(response.data.phoneNumbersList);
  };

  const columns = useMemo(
    () => [
      {
        Header: " ",
        columns: [
          {
            Header: "Phone Number",
            accessor: "phoneNumber"
          },
          {
            Header: "Country Name",
            accessor: "countryName"
          },
          {
            Header: "Country Code",
            accessor: "countryCode"
          },
          {
            Header: "State",
            accessor: "state"
          }
        ]
      }
    ],
    []
  );

  const handleChange = (e) => {
    setInputs({
      ...inputs,
      [e.target.name]: e.target.value
    });
  }

  const handleSubmit = (e) => { 
    e.preventDefault();
    getPhoneNumbersWithAxios(inputs.countryName, inputs.state);
  }

  const mapPhoneNumbers = (phoneNumbers) => {
    return phoneNumbers.map ((it) => ({ ...it, countryName: it.countryName? it.countryName : "unknown", state: it.valid ? "Valid" : "Invalid"})); 
  }
  const capitalizeCountyNames = (phoneNumbers) => {
    return phoneNumbers.map ((it) => ({...it, countryName: it.countryName.charAt(0).toUpperCase() + it.countryName.slice(1)})); 
  }

  const formatOutput = (phoneNumbers) => { 
    let mappedPhoneNumbers = mapPhoneNumbers(phoneNumbers);
    return capitalizeCountyNames(mappedPhoneNumbers);
  }

  return (
      <Container>
        <div className="form">
          <form onSubmit={handleSubmit}>
          <label>
            Country: 
            <select key="countryName" name="countryName" value={inputs.countryName} onChange={handleChange}>     
              <option value="" selected></option>     
              <option value="cameroon">Cameroon</option>
              <option value="ethiopia">Ethiopia</option>
              <option value="morocco">Morocco</option>
              <option value="mozambique">Mozambique</option>
              <option value="uganda">Uganda</option>
            </select>
          </label>

          <label>
            State:   
            <select key="state" name="state" value={inputs.state} onChange={handleChange}>      
              <option value="NONE" selected></option>              
              <option value="VALID">Valid</option>
              <option value="INVALID">Invalid</option>
            </select>
          </label>

          <input type="submit" value="Filter" className="button" />
          </form>
        </div>
        {phoneNumbers == null ? <div>Loading...</div> : (!phoneNumbers.length ? <div>No records found</div>  : <Table columns={columns} data={formatOutput(phoneNumbers)}/>) }
      </Container>)

}

export default App;