import { useLoginContext } from "../../contexts/LoginContext";
import { Table } from "../../components/Table";
import { useEffect, useState } from "react";
import api from "../../axios";
import { Link, Navigate } from "react-router-dom";
export function IndexGenres() {
  const { auth, loading } = useLoginContext();
  const [genres, setGenres] = useState();
  const server = import.meta.env.VITE_SERVER_ROOT;

  let headers = [];
  if (auth.role == "ADMIN") {
    headers = ["Id", "Genre", "Action"];
  } else {
    headers = ["Id", "Genre"];
  }

  useEffect(() => {
    api.get(`/api/genres`).then((response) => {
      console.log(response);
      setGenres(response.data);
    });
  }, []);

  return (
    <>
      {auth.role == "ADMIN" && (
        <>
          <h2 className="my-5 text-center">Explore genres! </h2>

          {auth.role == "ADMIN" && (
            <div className="d-flex justify-content-end me-3 mb-3">
              <Link className="btn btn-dark" to="/genres/create">
                Add new genre
              </Link>
            </div>
          )}

          <Table headers={headers} list={genres} type={"genres"}></Table>
        </>
      )}

      {auth?.role != "ADMIN" && !loading && <Navigate to={"/404"}></Navigate>}
    </>
  );
}
