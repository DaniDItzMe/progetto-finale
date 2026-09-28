import { BrowserRouter, Route, Routes } from "react-router-dom";
import { DefaultLayout } from "./layouts/DefaultLayout";
import { Index } from "./pages/games/Index";
import { Show } from "./pages/games/Show";
import { Login } from "./pages/Login";
import { CreateEdit } from "./pages/games/CreateEdit";
import { LoginProvider } from "./contexts/LoginContext";
import { NotFound } from "./pages/NotFound";
import { IndexGenres } from "./pages/genres/IndexGenres";
import { CreateEditGenres } from "./pages/genres/CreateEditGenres";
import { IndexConsoles } from "./pages/consoles/IndexConsoles";
import { CreateEditConsoles } from "./pages/consoles/CreateEditConsoles";
import { ShowConsoles } from "./pages/consoles/ShowConsoles";
function App() {
  return (
    <>
      <BrowserRouter>
        <LoginProvider>
          <Routes>
            <Route element={<DefaultLayout />}>
              <Route path="/" element={<Index />}></Route>
              <Route path="/login" element={<Login />}></Route>
              <Route path="/logout" element={<Login />}></Route>
              <Route path="/show/:id" element={<Show />}></Route>
              <Route path="/edit/:id" element={<CreateEdit />}></Route>
              <Route path="/create" element={<CreateEdit />}></Route>
              <Route path="/genres" element={<IndexGenres />}></Route>
              <Route
                path="/genres/create"
                element={<CreateEditGenres />}
              ></Route>
              <Route
                path="/genres/edit/:id"
                element={<CreateEditGenres />}
              ></Route>
              <Route path="/consoles" element={<IndexConsoles />}></Route>
              <Route
                path="/consoles/edit/:id"
                element={<CreateEditConsoles />}
              ></Route>
              <Route
                path="/consoles/create"
                element={<CreateEditConsoles />}
              ></Route>
              <Route
                path="/consoles/show/:id"
                element={<ShowConsoles />}
              ></Route>

              <Route path="/*" element={<NotFound />}></Route>
            </Route>
          </Routes>
        </LoginProvider>
      </BrowserRouter>
    </>
  );
}

export default App;
