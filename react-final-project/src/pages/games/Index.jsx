import { useEffect, useState } from "react";
import api from "../../axios";
import { Table } from "../../components/Table";
import { useSearchParams } from "react-router-dom";

export function Index() {
  const [games, setGames] = useState([]);
  const [filteredGames, setFilteredGames] = useState();
  const [searchParam, setSearchParam] = useSearchParams();
  const query = searchParam.get("search") || null;

  let headers = ["Cover", "Name", "Description", "Price"];

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
      <h2 className="my-5 text-center">Explore our games!</h2>

      <Table
        headers={headers}
        list={query ? filteredGames : games}
        type={"games"}
      ></Table>
    </>
  );
}
