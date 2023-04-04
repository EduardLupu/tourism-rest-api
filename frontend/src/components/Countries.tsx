import {
  Button,
  CircularProgress,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  Input,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import BACKEND_API_URL from "../constants";
import { Country } from "../models/Country";
import LibraryAddIcon from "@mui/icons-material/LibraryAdd";
import DeleteIcon from "@mui/icons-material/Delete";
import AppRegistrationIcon from "@mui/icons-material/AppRegistration";
import axios from "axios";

interface OpenState {
  [id: string]: boolean;
}

function Countries() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [countries, setCountries] = useState<Country[]>([]);
  const [open, setOpen] = useState<OpenState>({});
  const [country, setCountry] = useState<Country>({
    countryName: "",
    countrySurface: 0,
    countryPopulation: 0,
    countryAbbreviation: "",
  });

  const handleClick = (id: string) => () => {
    setOpen((open) => ({
      ...open,
      [id]: !open[id],
    }));
  };

  const updateCountry = async (countryId: number) => {
    try {
      await axios.put(`${BACKEND_API_URL}/countries/${countryId}`, country);
      navigate("");
    } catch (error) {
      console.log(error);
    }
    handleClick("updateCountry");
  };

  const deleteCountry = async (countryId: number) => {
    try {
      await axios.delete(`${BACKEND_API_URL}/countries/${countryId}`);
      navigate("");
    } catch (error) {
      console.log(error);
    }
  };

  const addCountry = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      await axios.post(`${BACKEND_API_URL}/countries`, country);
    } catch (error) {
      console.log(error);
    }
    open["addCountry"] = false;
  };

  useEffect(() => {
    setLoading(true);
    fetch(`${BACKEND_API_URL}/countries`)
      .then((response) => response.json())
      .then((data) => {
        setCountries(data);
        setLoading(false);
      });
  }, []);

  return (
    <Container>
      <div className="countries">
        <h2>Countries</h2>
        <Button onClick={handleClick("addCountry")}>
          <LibraryAddIcon />
        </Button>
      </div>
      <Dialog
        open={open["addCountry"]}
        keepMounted
        onClose={handleClick("addCountry")}
      >
        <DialogContent>Add a new country:</DialogContent>
        <DialogActions
          sx={{
            height: 300,
            width: 280,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "space-evenly",
          }}
        >
          <TextField
            id="countryname"
            label="Name"
            variant="outlined"
            sx={{ marginLeft: 1, marginTop: -2 }}
            onChange={(event) =>
              setCountry({ ...country, countryName: event.target.value })
            }
          />
          <TextField
            id="population"
            label="Population"
            variant="outlined"
            onChange={(event) =>
              setCountry({
                ...country,
                countryPopulation: parseInt(event.target.value),
              })
            }
          />
          <TextField
            id="surface"
            label="Surface"
            variant="outlined"
            onChange={(event) =>
              setCountry({
                ...country,
                countrySurface: parseInt(event.target.value),
              })
            }
          />
          <TextField
            id="abbreviation"
            label="Abbreviation"
            variant="outlined"
            onChange={(event) =>
              setCountry({
                ...country,
                countryAbbreviation: event.target.value,
              })
            }
          />
          <Button onClick={addCountry}>Add</Button>
        </DialogActions>
      </Dialog>
      {loading && <CircularProgress />}
      {!loading && countries.length === 0 && <p>No countries found</p>}
      {!loading && countries.length > 0 && (
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell>Name</TableCell>
                <TableCell>Population</TableCell>
                <TableCell>Surface</TableCell>
                <TableCell>Abbreviation</TableCell>
                <TableCell></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {countries.map((country, index) => (
                <TableRow key={country.countryId}>
                  <TableCell>{index + 1}</TableCell>
                  <TableCell>
                    <Link to={`/countries/${country.countryId}`}>
                      {country.countryName}
                    </Link>
                  </TableCell>
                  <TableCell>{country.countryPopulation}</TableCell>
                  <TableCell>{country.countrySurface}</TableCell>
                  <TableCell>{country.countryAbbreviation}</TableCell>
                  <TableCell sx={{ whiteSpace: "nowrap", maxWidth: 40 }}>
                    <Button
                      onClick={handleClick("updateCountry")}
                      defaultValue={country.countryId}
                    >
                      <AppRegistrationIcon />
                    </Button>
                    <Button
                      onClick={handleClick("deleteCountry")}
                      defaultValue={country.countryId}
                    >
                      <DeleteIcon />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
      <Dialog
        open={open["updateCountry"]}
        keepMounted
        onClose={handleClick("updateCountry")}
      >
        <DialogContent>Update the country:</DialogContent>
        <DialogActions
          sx={{
            height: 300,
            width: 280,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "space-evenly",
          }}
        >
          <TextField
            id="countryname"
            label="Name"
            variant="outlined"
            sx={{ marginLeft: 1, marginTop: -2 }}
            onChange={(event) =>
              setCountry({ ...country, countryName: event.target.value })
            }
          />
          <TextField
            id="population"
            label="Population"
            variant="outlined"
            onChange={(event) =>
              setCountry({
                ...country,
                countryPopulation: parseInt(event.target.value),
              })
            }
          />
          <TextField
            id="surface"
            label="Surface"
            variant="outlined"
            onChange={(event) =>
              setCountry({
                ...country,
                countrySurface: parseInt(event.target.value),
              })
            }
          />
          <TextField
            id="abbreviation"
            label="Abbreviation"
            variant="outlined"
            onChange={(event) =>
              setCountry({
                ...country,
                countryAbbreviation: event.target.value,
              })
            }
          />
          <Button>Update</Button>
        </DialogActions>
      </Dialog>
      <Dialog
        open={open["deleteCountry"]}
        keepMounted
        onClose={handleClick("deleteCountry")}
      >
        <DialogContent>
          Are you sure you want to delete this country?
        </DialogContent>
        <DialogActions>
          <Button>Cancel</Button>
          <Button>Delete</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
}

export default Countries;
