import { Table } from "../../components/Table";
import { useEffect, useState } from "react";
import api from "../../axios";
export function IndexGenres() {
  const [genres, setGenres] = useState(null);

  let headers = ["Id", "Genre"];

  useEffect(() => {
    api.get(`/api/genres`).then((response) => {
      console.log(response);
      setGenres(response.data);
    });
  }, []);

  return (
    <>
      <h2 className="my-5 text-center">Explore genres! </h2>

      {genres && (
        <Table headers={headers} list={genres} type={"genres"}></Table>
      )}
    </>
  );
}
