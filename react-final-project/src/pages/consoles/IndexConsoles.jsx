import { useLoginContext } from "../../contexts/LoginContext";
import { Table } from "../../components/Table";
import { useEffect, useState } from "react";
import api from "../../axios";
import { Link, Navigate } from "react-router-dom";

export function IndexConsoles() {
  const { auth } = useLoginContext();
  const [consoles, setConsoles] = useState();
  const server = import.meta.env.VITE_SERVER_ROOT;

  let headers = [];
  if (auth.role == "ADMIN") {
    headers = ["Id", "Console name", "Description", "Price", "Actions"];
  } else {
    headers = ["Id", "Console name", "Description", "Price"];
  }

  useEffect(() => {
    api.get(`/api/consoles`).then((response) => {
      console.log(response);
      setConsoles(response.data);
    });
  }, []);

  return (
    <>
      <h2 className="my-5 text-center">Explore consoles! </h2>

      {auth.role == "ADMIN" && (
        <div className="d-flex justify-content-end me-3 mb-3">
          <Link className="btn btn-dark" to="/consoles/create">
            Add new console
          </Link>
        </div>
      )}

      <Table headers={headers} list={consoles} type={"consoles"}></Table>
    </>
  );
}
