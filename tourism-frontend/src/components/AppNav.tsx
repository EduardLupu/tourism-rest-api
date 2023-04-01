import { Button } from "@mui/material";
import Box from "@mui/material/Box";
import React from "react";
import { Link } from "react-router-dom";
import HomeIcon from "@mui/icons-material/Home";

function AppNav() {
  return (
    <header className="header">
      <Link to="/">
        <Button>
          <HomeIcon />
        </Button>
      </Link>
      <Link to="/countries">
        <Button>Countries</Button>
      </Link>
      <Link to="/tourists">
        <Button>Tourists</Button>
      </Link>
    </header>
  );
}

export default AppNav;
