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
  TablePagination,
  TableRow,
  TableSortLabel,
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
import TablePaginationActions from "@mui/material/TablePagination/TablePaginationActions";

interface OpenState {
  [id: string]: boolean;
}

function Countries() {
  const navigate = useNavigate();
  const [filter, setFilter] = useState(""); // define filter state variable
  const [loading, setLoading] = useState(false);
  const [countries, setCountries] = useState<Country[]>([]);
  const [open, setOpen] = useState<OpenState>({});
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [page, setPage] = React.useState(0);
  const [country, setCountry] = useState<Country>({
    countryName: "",
    countrySurface: 0,
    countryPopulation: 0,
    countryAbbreviation: "",
  });

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

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

  const deleteCountry = async (countryId: number | undefined) => {
    open["deleteCountry"] = true;
    try {
      await axios.delete(`${BACKEND_API_URL}/countries/${countryId}`);
      navigate("/countries");
    } catch (error) {
      console.log(error);
    }
  };

  const addCountry = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      await axios.post(`${BACKEND_API_URL}/countries`, country);
      navigate("");
      countries.push(country);
    } catch (error) {
      console.log(error);
    }
    open["addCountry"] = false;
  };

  useEffect(() => {
    setLoading(true);
    const url = new URL(`${BACKEND_API_URL}/countries`);
    if (filter) {
      url.searchParams.set("population", filter);
    }
    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        setCountries(data);
        setLoading(false);
      });
  }, [filter]);

  function sortCountriesByColumn(
    columnName: keyof Country,
    countries: Country[],
    setCountries: React.Dispatch<React.SetStateAction<Country[]>>
  ) {
    if (
      columnName === "countryPopulation" ||
      columnName === "countrySurface" ||
      columnName === "countryName" ||
      columnName === "countryAbbreviation"
    ) {
      const sortedCountries = [...countries].sort((a, b) =>
        a[columnName] > b[columnName] ? 1 : -1
      );
      setCountries(sortedCountries);
    }
  }

  return (
    <Container>
      <div className="countries">
        <h2>Countries</h2>
        <label>
          Filter by population: &nbsp;
          <Input
            type="number"
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
            style={{ backgroundColor: "white", borderRadius: 10 }}
          />
        </label>
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
                <TableCell>
                  <Button
                    onClick={() =>
                      sortCountriesByColumn(
                        "countryName",
                        countries,
                        setCountries
                      )
                    }
                  >
                    Name
                  </Button>
                </TableCell>
                <TableCell>
                  <Button
                    onClick={() =>
                      sortCountriesByColumn(
                        "countryPopulation",
                        countries,
                        setCountries
                      )
                    }
                  >
                    Population
                  </Button>
                </TableCell>
                <TableCell>
                  <Button
                    onClick={() =>
                      sortCountriesByColumn(
                        "countrySurface",
                        countries,
                        setCountries
                      )
                    }
                  >
                    Surface
                  </Button>
                </TableCell>
                <TableCell>
                  <Button
                    onClick={() =>
                      sortCountriesByColumn(
                        "countryAbbreviation",
                        countries,
                        setCountries
                      )
                    }
                  >
                    Abbreviation
                  </Button>
                </TableCell>
                <TableCell></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {countries
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((country, index) => (
                  <TableRow key={country.countryId}>
                    <TableCell>{page * rowsPerPage + index + 1}</TableCell>
                    <TableCell>
                      <Link to={`/countries/${country.countryId}`}>
                        {country.countryName}
                      </Link>
                    </TableCell>
                    <TableCell>{country.countryPopulation}</TableCell>
                    <TableCell>{country.countrySurface}</TableCell>
                    <TableCell>{country.countryAbbreviation}</TableCell>
                    <TableCell
                      sx={{
                        whiteSpace: "nowrap",
                        maxWidth: 70,
                      }}
                    >
                      <Button onClick={handleClick("updateCountry")}>
                        <AppRegistrationIcon />
                      </Button>
                      <Button onClick={() => deleteCountry(country.countryId)}>
                        <DeleteIcon />
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
          <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={countries.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
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
          <Button onClick={handleClick("deleteCountry")}>Cancel</Button>
          <Button onClick={handleClick("deleteCountry")}>Delete</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
}

export default Countries;
