import React, { useEffect, useState } from "react";
import axios from 'axios';
import Navbar from "../navigation/Navbar";
import Grid from "react-fast-grid";
import MaterialTable from 'material-table'
import Table from './test'
const styles = {
    outer: {
        borderRadius: 5,
        boxShadow: "0 10px 30px #BBB",
        padding: 10,
    },
};

export default function Stats() {
    if (window.localStorage.getItem('logged') === null) {
        window.location.href = '/';
    }
    const [rows, setRows] = useState([]);

    function sendQuery(e) {
        e.preventDefault();
        let name = document.getElementById("name").value;
        let id = document.getElementById("id").value || "";
        let game = document.getElementById("game").value || "";
        axios.get("/playerstats", { params: { name: name, id: id, game: game } }).then(response => showData(response));
    }

    function showData(response) {
        let dbData = [];
        Object.values(response.data).map(item => dbData.push(item));
        setRows(dbData);
    }
    return (
        <div className="container-fluid">
            <Navbar />

            <form>
                <div style={styles.outer}>
                    <Grid container spacing={2} direction="row">
                        <Grid item sm={3} xs={6}>
                            <div>Name</div>
                            <input type="text" id="name" name="name" className="form-control"/>
                        </Grid>
                        <Grid item sm={3} xs={6}>
                            <div>ID</div>
                            <input type="text" id="id" name="id" className="form-control"/>
                        </Grid>
                        <Grid item sm={3} xs={6}>
                            <div>Game</div>
                            <input type="text" id="game" name="game" className="form-control"/>
                        </Grid>
                        <Grid item sm={3} xs={6}>
                            <button style={{ marginTop: 25 }} onClick={(e) => { sendQuery(e) }} className="btn"> Submit</button>
                        </Grid>
                    </Grid>
                </div>
            </form>
            <Table data={rows}></Table>
        </div >

    );
}
