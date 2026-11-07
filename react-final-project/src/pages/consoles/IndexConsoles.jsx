import { Table } from "../../components/Table";
import { useEffect, useState } from "react";
import api from "../../axios";

export function IndexConsoles() {
  const [consoles, setConsoles] = useState();

  let headers = ["Id", "Console name", "Description", "Price"];

  useEffect(() => {
    api.get(`/api/consoles`).then((response) => {
      console.log(response);
      setConsoles(response.data);
    });
  }, []);

  return (
    <>
      <h2 className="my-5 text-center">Explore consoles! </h2>

      <Table headers={headers} list={consoles} type={"consoles"}></Table>
    </>
  );
}
