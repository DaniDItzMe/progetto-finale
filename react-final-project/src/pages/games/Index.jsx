import { useEffect, useState } from "react";
import api from "../../axios";
import { Link } from "react-router-dom";
import { Table } from "../../components/Table";
import { useLoginContext } from "../../contexts/LoginContext";
import { useSearchParams } from "react-router-dom";

export function Index() {
  const [games, setGames] = useState([]);
  const [filteredGames, setFilteredGames] = useState();
  const { auth } = useLoginContext();
  const [searchParam, setSearchParam] = useSearchParams();
  const query = searchParam.get("search") || null;

  const server = import.meta.env.VITE_SERVER_ROOT;

  let headers = [];
  if (auth.role == "ADMIN") {
    headers = ["Cover", "Name", "Description", "Price", "Action"];
  } else {
    headers = ["Cover", "Name", "Description", "Price"];
  }

  useEffect(() => {
    api.get(`/api/games`).then((response) => {
      console.log(response.data);
      setGames(response.data);
    });
  }, []);

  useEffect(() => {
    if (query) {
      setFilteredGames(
        games.filter((game) =>
          game.name.toUpperCase().includes(query.toUpperCase()),
        ),
      );
    } else {
      setFilteredGames(null);
    }
  }, [games]);

  return (
    <>
      {auth.authenticated && (
        <h1 className="my-5 text-center">Welcome back {auth.username}!</h1>
      )}

      <h2 className="my-5 text-center">Explore our games!</h2>

      {auth.role == "ADMIN" && (
        <div className="d-flex justify-content-end me-3 mb-3">
          <Link className="btn btn-dark" to="/create">
            Add new game
          </Link>
        </div>
      )}

      <Table
        headers={headers}
        list={query ? filteredGames : games}
        type={"games"}
      ></Table>
      {/* <div>
        {filteredGames?.map((game) => (
          <p>{game.name}</p>
        ))}
      </div> */}
    </>
  );
}
