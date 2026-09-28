import api from "../../axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { ConsolesCreateEditForm } from "../../components/ConsolesCreateEditForm";
import { useLocation } from "react-router-dom";
import { useLoginContext } from "../../contexts/LoginContext";
import { useNavigate } from "react-router-dom";
import { Link, Navigate } from "react-router-dom";

export function CreateEditConsoles() {
  const { id } = useParams();

  const { auth, loading } = useLoginContext();
  const server = import.meta.env.VITE_SERVER_ROOT;

  const location = useLocation();

  const [edit, setEdit] = useState(
    location.pathname == "/consoles/create" ? false : true,
  );

  const navigate = useNavigate();

  const [gameConsole, setGameConsole] = useState(
    !edit
      ? {
          name: "",
          description: "",
          price: "",
        }
      : null,
  );

  useEffect(() => {
    if (edit) {
      api
        .get(`/api/consoles/get/${id}`)
        .then((response) => {
          console.log(response.data);
          setGameConsole(response.data);
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
          {edit && (
            <h1 className="my-5 text-center">Edit {gameConsole?.name}</h1>
          )}
          {!edit && <h1 className="my-5 text-center">Create new console</h1>}
          {gameConsole && (
            <div className="d-flex justify-content-center">
              <ConsolesCreateEditForm
                gameConsole={gameConsole}
                edit={edit}
              ></ConsolesCreateEditForm>
            </div>
          )}

          {!gameConsole && edit && (
            <div>
              <h1 className="text-center fw-bold text-danger">
                Console not found!
              </h1>
            </div>
          )}
        </>
      )}

      {auth?.role != "ADMIN" && !loading && <Navigate to={"/404"}></Navigate>}
    </>
  );
}
