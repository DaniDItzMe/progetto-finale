import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../../axios";
import { GameCard } from "../../components/GameCard";
import { useLoginContext } from "../../contexts/LoginContext";
import { Table } from "../../components/Table";
import { Link } from "react-router-dom";

export function ShowConsoles() {
  const [games, setGames] = useState();
  const [gameConsole, seGameConsole] = useState();

  const { auth } = useLoginContext();
  const server = import.meta.env.VITE_SERVER_ROOT;

  const { id } = useParams();

  let headers = [];
  if (auth.role == "ADMIN") {
    headers = ["Cover", "Name", "Description", "Price", "Action"];
  } else {
    headers = ["Cover", "Name", "Description", "Price"];
  }

  useEffect(() => {
    api.get(`/api/consoles/getGames/${id}`).then((response) => {
      console.log(response.data);
      setGames(response.data);
    });

    api
      .get(`/api/consoles/get/${id}`)
      .then((response) => {
        console.log(response.data);

        seGameConsole(response.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <>
      <h2 className="my-5 text-center">Explore our games! </h2>

      {auth.role == "ADMIN" && (
        <div className="d-flex justify-content-end me-3 mb-3">
          <Link className="btn btn-dark" to="/create">
            Add new game
          </Link>
        </div>
      )}

      {games && <Table headers={headers} list={games} type={"games"}></Table>}
    </>
  );
}
