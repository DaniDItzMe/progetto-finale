import api from "../../axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { GenresCreateEditForm } from "../../components/GenresCreateEditForm";
import { useLocation } from "react-router-dom";
import { useLoginContext } from "../../contexts/LoginContext";
import { useNavigate } from "react-router-dom";
import { Link, Navigate } from "react-router-dom";

export function CreateEditGenres() {
  const { id } = useParams();

  const { auth, loading } = useLoginContext();

  const location = useLocation();
  const server = import.meta.env.VITE_SERVER_ROOT;

  const [edit, setEdit] = useState(
    location.pathname == "/genres/create" ? false : true,
  );

  const navigate = useNavigate();

  const [genre, setGenre] = useState(
    !edit
      ? {
          name: "",
        }
      : null,
  );

  useEffect(() => {
    if (edit) {
      api
        .get(`/api/genres/get/${id}`)
        .then((response) => {
          console.log(response.data);
          setGenre(response.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);

  return (
    <>
      {auth?.role === "ADMIN" && (
        <>
          {edit && <h1 className="my-5 text-center">Edit {genre?.name}</h1>}
          {!edit && <h1 className="my-5 text-center">Create new genre</h1>}
          {genre && (
            <div className="d-flex justify-content-center">
              <GenresCreateEditForm
                genre={genre}
                edit={edit}
              ></GenresCreateEditForm>
            </div>
          )}

          {!genre && edit && (
            <div>
              <h1 className="text-center fw-bold text-danger">
                Genre not found!
              </h1>
            </div>
          )}
        </>
      )}

      {auth?.role != "ADMIN" && !loading && <Navigate to={"/404"}></Navigate>}
    </>
  );
}
