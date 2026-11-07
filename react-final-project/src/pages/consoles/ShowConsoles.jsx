import { useEffect, useState } from "react";
import { useParams, useSearchParams } from "react-router-dom";
import api from "../../axios";
import { Table } from "../../components/Table";

export function ShowConsoles() {
  const [games, setGames] = useState();
  const [gameConsole, seGameConsole] = useState();
  const [qsParams] = useSearchParams();
  const consoleName = qsParams.get("console");

  const { id } = useParams();

  let headers = ["Cover", "Name", "Description", "Price"];

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
      <h2 className="my-5 text-center">{consoleName} games!</h2>

      {games && <Table headers={headers} list={games} type={"games"}></Table>}
      {games == null && (
        <div>
          <h1 className="text-center text-danger fw-bold">
            No games available for this console yet.
          </h1>
        </div>
      )}
    </>
  );
}
