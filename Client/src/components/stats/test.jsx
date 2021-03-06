import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});

function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}

let rows = [
  // createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
  // createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
  // createData('Eclair', 262, 16.0, 24, 6.0),
  // createData('Cupcake', 305, 3.7, 67, 4.3),
  // createData('Gingerbread', 356, 16.0, 49, 3.9),
];

export default function BasicTable({ data }) {
  const classes = useStyles();
  console.log(data);

  rows = data;

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="right">Name</TableCell>
            <TableCell align="right">ID</TableCell>
            <TableCell align="right">Game</TableCell>
            <TableCell align="right">Level</TableCell>
            <TableCell align="right">Points</TableCell>
            <TableCell align="right">Progress</TableCell>
            <TableCell align="right">Missed Clicks</TableCell>
            <TableCell align="right">Completion Time</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.name}>
              {/* <TableCell component="th" scope="row">
                {row.name}
              </TableCell> */}
              <TableCell align="right">{row.player.name}</TableCell>
              <TableCell align="right">{row.player.id}</TableCell>
              <TableCell align="right">{row.game}</TableCell>
              <TableCell align="right">{row.level}</TableCell>
              <TableCell align="right">{row.points}</TableCell>
              <TableCell align="right">{row.progress}</TableCell>
              <TableCell align="right">{row.missedClicks}</TableCell>
              <TableCell align="right">{row.compTime}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
