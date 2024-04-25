<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase\Contract\Database;

class UserController extends Controller
{
    protected $database;
    protected $tablename;
    public function __construct(Database $database)
    {
        $this->database = $database;
        $this->tablename = 'users';
    }
    public function index() {
        $users = $this->database->getReference($this->tablename)->getValue();
        return view('users',compact('users'));
        // return dd(compact('users'));
    }
}
